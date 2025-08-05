package com.innochatbot.inno_chatbot;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@SpringBootApplication(scanBasePackages = "com.innochatbot")
@MapperScan("com.innochatbot.admin.mapper")
@Controller
//@PropertySource(value = "classpath:.env", ignoreResourceNotFound = true)
public class InnoChatbotApplication {

    public static void main(String[] args) {
        SpringApplication.run(InnoChatbotApplication.class, args);
    }
    
    @RequestMapping("/")
    public String index() {
    //나중에 챗봇은 여기에 띄우면 좋겠다.
    	return "thymeleaf/리스트";
    } 

}
 