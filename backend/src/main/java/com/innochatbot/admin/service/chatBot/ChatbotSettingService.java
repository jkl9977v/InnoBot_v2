package com.innochatbot.admin.service.chatBot;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.innochatbot.admin.command.ChatbotCommand;
import com.innochatbot.admin.dto.ChatbotDTO;
import com.innochatbot.admin.mapper.ChatbotMapper;

@Service
public class ChatbotSettingService {
	@Autowired
	ChatbotMapper chatbotMapper;

	public void settingUpdate(ChatbotCommand command) {
	    //Optional<ChatbotDTO> existing = chatbotMapper.findById("default");
	    ChatbotDTO dto = new ChatbotDTO();
	    
	    dto.setSettingId(command.getSettingId());
	    dto.setPath(command.getPath());
	    dto.setHour(command.getHour());
	    dto.setMin(command.getMin());
	    
	    //확장자
	    /*
	    dto.setTxt(command.getTxt());
	    dto.setPdf(command.getPdf());
	    dto.setDocx(command.getDocx());
	    dto.setXlsx(command.getXlsx());
	    dto.setPptx(command.getPptx());
	    dto.setHtml(command.getHtml());
	    dto.setCvs(command.getCvs());
	    dto.setTika(command.getTika());
	    */
	    
	    chatbotMapper.botSettingUpdate(dto);
		
	}
	
	/*
	String extCsv = dto.getFileExtensions(); // ".pdf,.txt,.docx"
List<String> extList = Arrays.stream(extCsv.split(","))
                             .map(String::trim)
                             .filter(s -> !s.isEmpty())
                             .collect(Collectors.toList());
                             */

}
