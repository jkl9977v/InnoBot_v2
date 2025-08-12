package com.innochatbot.api.service;

import java.nio.file.Path;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.innochatbot.admin.dto.FileDTO;
import com.innochatbot.admin.mapper.FilePathMapper;
import com.innochatbot.admin.service.AutoNumService;
import com.innochatbot.api.mapper.ChunkMapper;
import com.innochatbot.api.mapper.FileMapper;
import com.innochatbot.api.utill.FileHashUtill;

@Service
public class FileScannerService {

	@Autowired
	FilePathMapper filePathMapper;
	@Autowired
	FileMapper fileMapper;
	@Autowired
	AutoNumService autoNumService;
	@Autowired
	FileTextEmbeddingService fileTextEmbeddingService;
	@Autowired
	ChunkMapper chunkMapper;
	
	//개별  파일 처리 함수
	public void processFile(String fileName, String filePath, Path parentPath
			, Date updateTime, long size, Path currentPath) {
        System.out.println("처리대상 파일: " + filePath);
        System.out.println("파일명: " + fileName);

		try {
			//현재 파일 Id 조회
			String fileId = getFileId(fileName, currentPath);
			//현재 파일 hash 계산
			String currentHash = FileHashUtill.getMD5(currentPath);
			//PathId 조회
			String pathId = getPathId(parentPath);
			//extension
			String extension = "";
			int dotIndex = fileName.lastIndexOf('.');
			if (dotIndex != -1 && dotIndex <fileName.length() -1) {
				extension = fileName.substring(dotIndex + 1); //확장자만 추출
			}
			System.out.println(extension);
			
			if (fileId != null) {
				//기존 파일 hash 계산
				String oldHash = fileHashSelect(fileId);
				if (currentHash.equals(oldHash)) {
		            System.out.println("파일 변경 없음 -> 생략: " + fileName);
		            return;
				} else if (!currentHash.equals(oldHash)) {
					fileMapper.fileHashUpdate(currentHash, fileId);
					
					//기존 Chunk삭제
					chunkMapper.chunkDelete(fileId);
					fileTextEmbeddingService.contentEmbedding(currentPath, extension, fileId);
				}
			} else if (fileId == null) { // 1. file_id가 null일때 : file 이번에 감지함
	            // String text;
	            // List<String> chunks;    		
	            String sep ="file_";
	            String column = "file_id";
	            int len = 6;
	            String table = "file";
	            int zeroLen = 10;
	            fileId = autoNumService.autoNum2(sep, column, len, table , zeroLen); 
	              
	            //file에 대한 insert 부분 만들기 
	            FileDTO dto = new FileDTO();
	                
	            dto.setFileId(fileId);
	            dto.setFileName(fileName);
	            dto.setHash(currentHash);
	            dto.setExtension(extension);
	            dto.setPathId(pathId);
	            dto.setSize(size);
	            dto.setUpdateTime(updateTime); 
	                
	            fileMapper.fileInsert(dto);
	            System.out.println(fileName + " 파일 테이블 입력 완료");
	            
	            fileTextEmbeddingService.contentEmbedding(currentPath, extension, fileId); 
	                
			}

	    		
	    		//파일 확장자별 처리 (스위치 케이스 또는 if문으로 만들기)
	    		
	    		
	            //processPDF 로 들어갈 부분
	    		
	    		/*
	            // ① PDF 텍스트 추출
	            try (PDDocument doc = PDDocument.load(filePath.toFile())) {
	                text = new PDFTextStripper().getText(doc);
	                // ② 텍스트를 400자 단위로 분할
	                chunks = chunkService.split(text, 400);
	            }
	            
	            chunkMapper.chunkDelete(fileId);
	            
	            //chunk부분으로 나갈 부분
	            // ④ 각 청크에 대해 임베딩 벡터 생성 + DB 저장
	            for (int i = 0; i < chunks.size(); i++) {
	                float[] vec = embeddingService.embed(chunks.get(i));
	                chunkService.saveChunk(fileId, i + 1, chunks.get(i), vec);
	            }
	            */
	
	            
	            //System.out.printf("  • 처리 완료: %s (%d 청크)%n", fileName, chunks.size());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        }
	
		private String getPathId(Path parentPath) {
			String path = parentPath.toAbsolutePath().toString().replace("\\", "/");
			System.out.println("PathId 조회, path = " + path);
			String pathId = filePathMapper.pathIdSelect(path);
			if (pathId == null ) {
				return pathId = null;
			}
			else return pathId;
		}

		//파일명 + 경로기반 file_id 조회
		private String getFileId(String fileName, Path currentPath) {
			String fullPath = currentPath.toAbsolutePath().toString().replace("\\", "/"); //Windows 경로 처리
			String path = fullPath.replace("/" + fileName, "");
			System.out.println("path: " + path);
			
			String fileId = fileMapper.fileIdSelect(fileName, path);
			if (fileId == null) {
				fileId = null;
			}
			return fileId;
		}
	        
	    //DB에서 기존 해시 조회
	    public String fileHashSelect(String fileId) {
	    	String hash = fileMapper.filehashSelect(fileId);
	    	if(hash != null) {
	    		return hash;
	    	}else return null;
	    }

}
