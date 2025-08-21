package com.innochatbot.admin.service.chatBot;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.innochatbot.admin.dto.ChatbotDTO;
import com.innochatbot.admin.mapper.ChatbotMapper;

@Service
public class ChatbotSettingService {
	@Autowired
	ChatbotMapper chatbotMapper;

	public void saveOrUpdate(ChatbotDTO dto) {
	    Optional<ChatbotDTO> existing = chatbotMapper.findById("default");
	    ChatbotDTO setting = existing.orElse(new ChatbotDTO());
	    
	    setting.setId("default"); // 단일 설정용
	    setting.setModelName(dto.getModelName());
	    setting.setEmbeddingModel(dto.getEmbeddingModel());
	    setting.setRefreshCycleMin(dto.getRefreshCycleMin());
	    setting.setFileExtensions(dto.getFileExtensions());
	    
	    chatbotMapper.save(dto);
		
	}
	
	/*
	String extCsv = dto.getFileExtensions(); // ".pdf,.txt,.docx"
List<String> extList = Arrays.stream(extCsv.split(","))
                             .map(String::trim)
                             .filter(s -> !s.isEmpty())
                             .collect(Collectors.toList());
                             */

}
