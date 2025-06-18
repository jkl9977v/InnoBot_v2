package com.innochatbot.inno_chatbot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.innochatbot")
public class InnoChatbotApplication {

	public static void main(String[] args) {
		SpringApplication.run(InnoChatbotApplication.class, args);
	}

}
