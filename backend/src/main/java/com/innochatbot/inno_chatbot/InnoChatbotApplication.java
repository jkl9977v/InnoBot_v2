package com.innochatbot.inno_chatbot;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.innochatbot")
@MapperScan("com.innochatbot.admin.mapper")
public class InnoChatbotApplication {

    public static void main(String[] args) {
        SpringApplication.run(InnoChatbotApplication.class, args);
    }

}
