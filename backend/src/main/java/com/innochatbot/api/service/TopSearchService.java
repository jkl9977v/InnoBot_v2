package com.innochatbot.api.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.innochatbot.api.dto.TopChunk;
import com.innochatbot.api.dto.TopFile;
import com.innochatbot.api.mapper.ChatMapper;
import com.innochatbot.api.utill.VectorUtils;

@Service
public class TopSearchService { //유사도 검색 서비스
	@Autowired
	ChatMapper chatMapper;
	@Autowired
	EmbeddingService embeddingService;
	
	//file -> chunk 순서로 유사도 검색
	public List<TopChunk> topFileChunk(float[] qvec, int M, int K){
		
		//1단계: 제목 임베딩 조회
		List<Map<String, Object>> fileRows = chatMapper.fileNameEmbeddingSelect();
		
		PriorityQueue<TopFile> topM = new PriorityQueue<>(Comparator.comparingDouble(f -> f.score()));
		
		for (Map<String, Object> row : fileRows ) {
			float[] titleVec = VectorUtils.bytesToFloatArray((byte[]) row.get("title_embedding"));
			double score = VectorUtils.cosine(qvec, titleVec);
			
			TopFile topFile = new TopFile(
				(String) row.get("file_id"),
				(String) row.get("file_name"),
				score
			);
			
			if(topM.size() < M) {
				topM.add(topFile);
			}else if (score > topM.peek().score()) {
				topM.poll();
				topM.add(topFile);
			}
			
		}
		
		List<String> candidateFileIds = topM.stream()
				.sorted((a,b) -> Double.compare(b.score(), a.score()))
				.map(TopFile::fileId)
				.toList();
		
		//2단계: 후보 파일의 청크 임베딩 조회
		List<Map<String, Object>> chunkRows = chatMapper.chunkEmbeddingSelect(candidateFileIds);
		
		PriorityQueue<TopChunk> topK = new PriorityQueue<>(Comparator.comparingDouble(c->c.score()));
		
		for(Map<String, Object> row : chunkRows) {
			float[] chunkVec = VectorUtils.bytesToFloatArray((byte[]) row.get("embedding"));
			double score = VectorUtils.cosine(qvec, chunkVec);
			
			TopChunk topChunk = new TopChunk(
				(String) row.get("chunk_id"),
				(String) row.get("file_id"),
				(String) row.get("content"),
				score
				
			);
			
			if(topK.size() < K) {
				topK.add(topChunk);
			} else if (score > topK.peek().score()) {
				topK.poll();
				topK.add(topChunk);
			}
		}
		
		return topK.stream()
				.sorted((a,b) -> Double.compare(b.score(), a.score()))
				.toList();
	}
	
    public List<TopChunk> topFileChunkEmbedding(float[] qvec, double wTitle, double wChunk, int K) {

        List<Map<String, Object>> rows = chatMapper.fileAndChunkEmbeddingSelect();
        PriorityQueue<TopChunk> topK = new PriorityQueue<>(Comparator.comparingDouble(c -> c.score()));

        for (Map<String, Object> row : rows) {
            float[] titleVec = VectorUtils.bytesToFloatArray((byte[]) row.get("title_embedding"));
            float[] chunkVec = VectorUtils.bytesToFloatArray((byte[]) row.get("embedding"));

            double st = VectorUtils.cosine(qvec, titleVec);
            double sc = VectorUtils.cosine(qvec, chunkVec);

            double score = wTitle * st + wChunk * sc;

            TopChunk topChunk = new TopChunk(
                (String) row.get("chunk_id"),
                (String) row.get("file_id"),
                (String) row.get("content"),
                score
            );

            if (topK.size() < K) {
                topK.add(topChunk);
            } else if (score > topK.peek().score()) {
                topK.poll();
                topK.add(topChunk);
            }
        }

        return topK.stream()
                .sorted((a, b) -> Double.compare(b.score(), a.score()))
                .toList();
    }
    
    public List<TopChunk> topChunkEmbeddingBasic(String query, int candidateLimit, int topK) {
        // 1) 사용자 질문 → 질의 임베딩
        float[] queryVec = embeddingService.embed(query);  // ===> 이게 queryVec
        // (선택) 정규화해두면 dot로 빠르게 계산 가능
        // VectorUtils.l2NormalizeInPlace(queryVec);

        // 2) DB에서 후보 청크 가져오기 (embedding은 byte[])
        List<Map<String, Object>> rows = chatMapper.selectChunkCandidates(candidateLimit);

        // 3) 후보 순회하며 코사인 유사도 계산 → Top‑K 유지 (작은 값이 먼저 나오는 min-heap)
        PriorityQueue<TopChunk> heap =
                new PriorityQueue<>(Comparator.comparingDouble(TopChunk::score));

        for (Map<String, Object> row : rows) {
            String chunkId = String.valueOf(row.get("chunk_id"));
            String fileId  = String.valueOf(row.get("file_id"));
            String content = (String) row.get("content");
            byte[] embBytes = (byte[]) row.get("embedding");

            if (embBytes == null || embBytes.length == 0) continue;

            // byte[] → float[]  ===> 이게 chunkVec
            float[] chunkVec = VectorUtils.bytesToFloatArray(embBytes);

            // (선택) 저장 시 정규화 안 했다면 여기서 정규화 가능
            // VectorUtils.l2NormalizeInPlace(chunkVec);

            // 4) 유사도 계산 (cosine 또는 dot)
            double score = VectorUtils.cosine(queryVec, chunkVec);
            // double score = VectorUtils.dot(queryVec, chunkVec); // 둘 다 정규화되어 있으면 동일

            // 5) Top‑K 유지
            if (heap.size() < topK) {
                heap.add(new TopChunk(chunkId, fileId, content, score));
            } else if (score > heap.peek().score()) {
                heap.poll();
                heap.add(new TopChunk(chunkId, fileId, content, score));
            }
        }

        // 6) 점수 내림차순 정렬하여 반환
        List<TopChunk> result = new ArrayList<>(heap);
        result.sort((a, b) -> Double.compare(b.score(), a.score()));
        return result;
    }
}
