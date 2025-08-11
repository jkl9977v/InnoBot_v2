package com.innochatbot.api.service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.util.Base64;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.innochatbot.admin.controller.UserController;
import com.innochatbot.api.mapper.ChunkMapper;
import com.innochatbot.api.mapper.FileMapper;

@Service
public class FileScannerService {

    private final UserController userController;
	@Autowired
	FileMapper fileMapper;
	@Autowired
	ChunkService chunkService;
	@Autowired
	EmbeddingService embeddingService;
	@Autowired
	ChunkMapper chunkMapper;

    FileScannerService(UserController userController) {
        this.userController = userController;
    }
	/*
	//변경된 PDF 파일만 반환
	//추후 전체 파일로 적용되게 해야 함
	public List<Path> getChangedPdfFiles(String docsDirPath){
		List<Path> result = new ArrayList<>();
		
		try {
			Path docsDir = Paths.get(docsDirPath);
			
			Files.walk(docsDir)
			.filter(p -> p.toString().endsWith(".pdf"))
			.forEach(p -> {
				//현재 파일 hash 계산, file_id 와 그 파일의 기존 해시를 조회한다.
				try {
					//현재 파일 hash 계산
					String currentHash = getFileHash(p);
					//file_path 테이블을 기준으로 file_id를 조회한다.
					String fileId = getFileId(p.getFileName().toString(), p);
					//파일의 기존해시를 조회하여 비교한다. (수정 여부를 확인하기 위해)
					String oldHash = getFileHashFromDb(fileId);
					
					if(fileId != null && currentHash.equals(oldHash)) { 
						//fileId가 있고, 새 hash와 기존 hash가 같을때
						System.out.println("파일 변경 없음 -> 생략: " + p.getFileName());
						
					}else {
						result.add(p);
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
	*/
	
	 // 개별 PDF 파일 처리 함수 => 개별 파일 처리 함수로 바꾸면 된다.
	//private
    public void processPdf(Path filePath) {
        System.out.println("처리대상 PDF: " + filePath.toAbsolutePath());
        System.out.println("파일명: " + filePath.getFileName().toString());
        System.out.println("filePath: " + filePath);

        //현재 파일 hash 계산, file_id와 그 파일의 기존 해시 조회한다.
        try {

            //현재 파일 hash 계산
            String currentHash = getFileHash(filePath);
            //file_path 테이블을 기준으로 file_id 조회, 파일의 기존 해시 조회(수정 여부 확인하기 위해)
            String fileId = getFileId(filePath.getFileName().toString(), filePath);
            String oldHash = getFileHashFromDb(fileId);
            if (fileId != null && currentHash.equals(oldHash)) {
                System.out.println("파일 변경 없음 -> 생략: " + filePath.getFileName());
                return;
            } else {
                String text;
                List<String> chunks;

                // ① PDF 텍스트 추출
                try (PDDocument doc = PDDocument.load(filePath.toFile())) {
                    text = new PDFTextStripper().getText(doc);
                    // ② 텍스트를 400자 단위로 분할
                    chunks = chunkService.split(text, 400);
                }

                if (fileId == null) {
                    System.err.println("fileId 조회 실패: file 테이블 확인" + fileId);
                    return;
                }
                
                chunkMapper.chunkDelete(fileId);

                // ④ 각 청크에 대해 임베딩 벡터 생성 + DB 저장
                for (int i = 0; i < chunks.size(); i++) {
                    float[] vec = embeddingService.embed(chunks.get(i));
                    chunkService.saveChunk(fileId, i + 1, chunks.get(i), vec);
                }

                //file 테이블의 hash 갱신
                fileMapper.updateFileHash(currentHash, fileId);
                System.out.printf("  • 처리 완료: %s (%d 청크)%n", filePath.getFileName(), chunks.size());

            }
        } catch (Exception e) {
            System.err.printf("  ! 오류: %s → %s%n", filePath.getFileName(), e.getMessage());
        }
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
		String hash = fileMapper.filehashSelect(fileId);
		if(hash != null) {
			return hash;
		}else return null;
	}
	
	//파일명 + 경로기반 file_id 조회
	//path를 기반으로 path_id를 조회한 다음, path_id가 있으면 file를 조회하는 방식으로 바꿔야 할것 같음
	public String getFileId(String fileName, Path filePath) {
		String fullPath = filePath.toAbsolutePath().toString().replace("\\", "/"); //Windows 경로 처리
		String path = fullPath.replace("/" + fileName, "");
		System.out.println("path: " + path);
		
		String fileId = fileMapper.fileIdSelect(fileName, path);
		return fileId.isEmpty() ? null : fileId;
	}
	
	//DB에 현재 파일의 해시 업데이트
	public void updateFileHash(String fileId, Path filePath) throws Exception {
		String newHash = getFileHash(filePath);
		fileMapper.updateFileHash(newHash, fileId);
	}
}
