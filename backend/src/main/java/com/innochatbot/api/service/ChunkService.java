package com.innochatbot.api.service;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.innochatbot.api.dto.ChunkDTO;
import com.innochatbot.api.mapper.ChunkMapper;

@Service
public class ChunkService {
	@Autowired
	private EmbeddingService embeddingService;
	@Autowired
	private JdbcTemplate jdbc;
	@Autowired
	ChunkMapper chunkMapper;
	
	//청크 분할
	public List<String> split(String text, int size){
		List<String> result = new ArrayList<>();
		int pos = 0;
		while(pos < text.length()) {
			int end = Math.min(pos + size, text.length());
			result.add(text.substring(pos,end));
			pos = end;
		}
		return result;
	}
	
	//모든 청크 저장
	public void saveChunks(String fileId, List<String> chunks) {
		//기존 Chunk삭제
		jdbc.update("DELETE FROM chunk WHERE file_id = ? ", fileId);
		
		for(int i = 0; i <chunks.size(); i++) {
			float[] vector = embeddingService.embed(chunks.get(i));
			saveChunk(fileId, i + 1, chunks.get(i), vector);
		}
	}
	
	private Long generateChunkId() {
		return Math.abs(UUID.randomUUID().getMostSignificantBits());
	}
	
	private void saveChunk(String fileId, int sequence, String content, float[] embedding) {
		Long chunkId = generateChunkId();
		ChunkDTO dto = new ChunkDTO();
		dto.setChunkId(chunkId);
		dto.setFileId(fileId);
		dto.setSequence(sequence);
		dto.setContent(content);
		dto.setEmbeddeing(toBytes(embedding));
		
		chunkMapper.chunkInsert(dto);
	}
	
	private byte[] toBytes(float[] vec) {
		ByteBuffer buffer = ByteBuffer.allocate(vec.length * 4);
		buffer.order(ByteOrder.LITTLE_ENDIAN);
		for(float v : vec) buffer.putFloat(v);
		return buffer.array();
		
	}
	
}
