package com.innochatbot.api.dto;

//요청DTO
public record ChatRequest(String question){

    
}

/*
package com.innochatbot.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ChatRequest {
    @JsonProperty("question")
    private String question;

    public ChatRequest() { //Jackson용 기본 생성자 
     }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }
}
 */