package com.innochatbot.api.component;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import org.springframework.stereotype.Component;

@Component
public class TxtTextExtractor {

	public String txt(Path filePath) throws Exception {
		String text = String.join("\n", Files.readAllLines(filePath, StandardCharsets.UTF_8));
		return text;
	}

}
