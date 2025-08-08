package com.innochatbot.api.component;

import java.io.File;
import java.nio.file.Path;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Component;

@Component
public class PdfTextExtractor {
	
	public String extract(Path filePath) throws Exception {
		File file = filePath.toFile();
		try(PDDocument document = PDDocument.load(file)){
			PDFTextStripper stripper = new PDFTextStripper();
			return stripper.getText(document);
		}
	}
}
