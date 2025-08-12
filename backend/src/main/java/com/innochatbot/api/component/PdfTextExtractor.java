package com.innochatbot.api.component;

import java.io.File;
import java.nio.file.Path;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Component;

@Component
public class PdfTextExtractor { //PDF 텍스트 추출기
	
	public String pdf(Path filePath) throws Exception {
		File file = filePath.toFile();
		try(PDDocument document = PDDocument.load(file)){ //pdf 파일을 열어서 다룰 수 있는 객체로 변환
			PDFTextStripper stripper = new PDFTextStripper();
			String text = stripper.getText(document);
			return text;
		}
	}
}
