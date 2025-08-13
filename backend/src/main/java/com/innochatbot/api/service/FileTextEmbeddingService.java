package com.innochatbot.api.service;

import java.nio.file.Path;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.innochatbot.api.component.PdfTextExtractor;
import com.innochatbot.api.component.TxtTextExtractor;

@Service
public class FileTextEmbeddingService {
	@Autowired
	PdfTextExtractor pdfTextExtractor;
	@Autowired
	ChunkService chunkService;
	@Autowired
	TxtTextExtractor txtTextExtractor;

	public void contentEmbedding(Path filePath, String extension, String fileId) throws Exception {
		String text;
		List<String> chunks;
		System.out.println("extension: " + extension);
		
		switch (extension) {
    	case "pdf":
			text = pdfTextExtractor.pdf(filePath);
			chunks = chunkService.split(text, 400);
			chunkService.saveChunks(fileId, chunks); 
    		break; 
    	case "txt" : 
			text = txtTextExtractor.txt(filePath);
			chunks = chunkService.split(text, 400);
			chunkService.saveChunks(fileId, chunks);
    		break;
    	case "docs" : 
    		//docsProcessor.process(filePath);
    		break;  
    	default : 
    		System.out.println("지원하지 않는 확장자: " + extension);
    	}
		
		
		
		
	}

}
