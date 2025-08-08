package com.innochatbot.api.service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.innochatbot.admin.dto.FileDTO;
import com.innochatbot.api.mapper.File_FilePathMapper;

@Service
public class FileScannerService {
	@Autowired
	private JdbcTemplate jdbc;
	@Autowired
	File_FilePathMapper file_FilePathMapper;
	
	//변경된 PDF 파일만 반환
	//추후 전체 파일로 적용되게 해야 함
	public List<Path> getChangedPdfFiles(String docsDirPath){
		List<Path> result = new ArrayList<>();
		try {
			Path docsDir = Paths.get(docsDirPath);
			
			Files.walk(docsDir)
			.filter(p -> p.toString().endsWith(".pdf"))
			.forEach(p -> {
				try {
					String fileId = getFileId(p.getFileName().toString(), p);
					String currentHash = getFileHash(p);
					String oldHash = getFileHashFromDb(fileId);
					
					if(fileId == null || !currentHash.equals(oldHash)) {
						result.add(p);
					}else {
						System.out.println("파일 변경 없음 -> 생략: " + p.getFileName());
					}
				}catch(Exception e){
					System.err.println("파일 비교 실패: " + p + " -> " + e.getMessage());
				}
			});
		}catch (Exception e) {
			System.err.println("파일 탐색 실패: " + e.getMessage());
		}
		return result;
	}
	
	//파일의 해시값 계산 (MD5 -> Base64)
	public String getFileHash(Path filePath) throws Exception {
		byte[] fileBytes = Files.readAllBytes(filePath);
		MessageDigest md = MessageDigest.getInstance("MD5");
		byte[] digest = md.digest(fileBytes);
		return Base64.getEncoder().encodeToString(digest);
	}
	
	//DB에서 기존 해시 조회
	public String getFileHashFromDb(String fileId) {
		String hash = file_FilePathMapper.filehashSelect(fileId);
		if(hash != null) {
			return hash;
		}else return null;
	}
	
	//파일명 + 경로기반 file_id 조회
	//path를 기반으로 path_id를 조회한 다음, path_id가 있으면 file를 조회하는 방식으로 바꿔야 할것 같음
	public String getFileId(String fileName, Path filePath) {
		FileDTO dto = new FileDTO();
		String fullPath = filePath.toAbsolutePath().toString().replace("\\", "/");
		String path = fullPath.replace("/" + fileName, "");
		
		List<String> fileId = file_FilePathMapper.fileIdSelect(fileName, path);
		return fileId.isEmpty() ? null : fileId.get(0);
	}
	
	//DB에 현재 파일의 해시 업데이트
	public void updateFileHash(String fileId, Path filePath) throws Exception {
		String newHash = getFileHash(filePath);
		jdbc.update("UPDATE file SET hash = ? WHERE file_id = ? ", newHash, fileId);
	}
}
