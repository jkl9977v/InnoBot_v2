package com.innochatbot.admin.dto;

import org.apache.ibatis.type.Alias;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Alias("chatbotDTO")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatbotDTO {
	
	String modelName;
	String embeddingModel;
	String refreshCycleMin;
	String fileExtensions;
	String id;

}
