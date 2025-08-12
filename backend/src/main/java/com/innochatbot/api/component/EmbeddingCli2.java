package com.innochatbot.api.component;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.innochatbot.api.mapper.FileMapper;
import com.innochatbot.api.service.ChunkService;
import com.innochatbot.api.service.EmbeddingService;
import com.innochatbot.api.service.FileScannerService;

/**
 * Spring Boot 애플리케이션 실행 직후 자동 실행되는 일괄처리 컴포넌트 1. docs 폴더 내 PDF 파일을 순회하며 2. 텍스트
 * 추출 → 청크 분할 → 임베딩 벡터 생성 3. DB의 chunk 테이블에 저장
 */
/*
@Component
public class EmbeddingCli2 implements CommandLineRunner {        // 텍스트 임베딩 기능을 수행한다.
    //PDF파일을 읽고, 400자 단위로 나눈 다음, OpenAI 임베딩 벡터를 생성하고, chunk테이블에 저장하는 일괄처리(batch)파일

    @Value("${openai.api.key}")         // application.properties 또는 .env에서 API 키 주입
    private String apiKey;
    
    @Autowired
    FileMapper fileMapper;
    
    @Autowired
    private FileScannerService fileScannerService;

    @Autowired
    private EmbeddingService embeddingService;
    
    @Autowired
    private PdfTextExtractor pdfTextExtractor;
    
    @Autowired
    private ChunkService chunkService;
 */   
    /*
    @Override
    public void run(String... args) throws Exception { //파일경로 순회
        System.out.println("▶ EmbeddingCli 시작");
        Files.walk(docsDir)
                //.filter(p -> p.toString().endsWith(".pdf")) // .pdf 확장자만 선택
                .forEach(filePath -> fileScannerService.processPdf(filePath));      // 각 PDF 처리를 위한 파일경로 전달
                
        Files.walk(docsDir)
        
        .forEach(filePath -> {
            //fileScannerService.registerFile(filePath); // DB 등록
            //processByExtension(filePath);              // 확장자별 처리
        });

        System.out.println("▶ EmbeddingCli 완료");
    }
    */
    /*
    public void registerFile(Path filePath) {
    	String fileName = filePath.getFileName().toString();
    	String pathStr = filePath.getParent().toString().replace("\\","/");
    	
    	//file_path insert or get path_id
    	//String pathId = getOrInsertFilePath(pathStr);
    	
    	//file insert
    	String fileId;
    */
    	
    	
    	//jdbc.update("INSERT INTO file(file_id, file_name, extension, path_id, size) VALUES (?, ?, ?, ?, ?)",
    	//        fileId, fileName, getExtension(fileName), pathId, filePath.toFile().length());
    //}
    
    //private void processByExtension(Path filePath) {
    	
    	//String ext = getExtension(filePath.getFileName().toString()).toLowerCase();
    	/*
    	switch (ext) {
    	case "pdf":
    		pdfProcessor.process(filePath);
    		break;
    	case "txt" : 
    		textProcessor.process(filePath);
    		break;
    	case "docs" : 
    		docsProcessor.process(filePath);
    		break;
    	default : 
    		System.out.println("지원하지 않는 확장자: " + ext);
    	}
    	*/
    //}
    
    
    
//}
