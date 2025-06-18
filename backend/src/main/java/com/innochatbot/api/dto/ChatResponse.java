package com.innochatbot.api.dto;

import java.util.List;

//응답DTO
public record ChatResponse(String answer, List<Long> sourceChunkIds){


}