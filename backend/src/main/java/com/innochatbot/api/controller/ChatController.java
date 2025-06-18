package com.innochatbot.api.controller;

import com.innochatbot.api.dto.ChatRequest;
import com.innochatbot.api.dto.ChatResponse;
import com.innochatbot.api.service.ChatService;
//import com.innochatbot.api.service.EmbeddingService;
//import org.springframework.beans.factory.annotation.*;
import org.springframework.web.bind.annotation.*;
//import java.util.*;

@CrossOrigin(origins="http://localhost:5173") //프론트엔드 주소에 맞게 조정
@RestController
@RequestMapping("/chat")
public class ChatController {

    // @Value("${openai.api.key}")
    // private String apiKey;

    // @Autowired
    // private EmbeddingService embeddingService; //2-4-1

    //@Autowired
    private final ChatService chatService;
    // private ChatService chatService; //2-4-2

    public ChatController (ChatService chatService){
        this.chatService=chatService;
    }

    @PostMapping
    public ChatResponse chat(@RequestBody ChatRequest req){
        return chatService.handle(req.question());
    }
}