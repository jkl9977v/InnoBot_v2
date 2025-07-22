package com.innochatbot.admin.service.allowg;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.innochatbot.admin.command.GradeCommand;
import com.innochatbot.admin.dto.GradeDTO;
import com.innochatbot.admin.mapper.GradeMapper;

@Service
public class AllowgWriteService {
	@Autowired
	GradeMapper gradeMapper;

	public void allowgWrite(GradeCommand gradeCommand) {
		GradeDTO dto = new GradeDTO();
		
		dto.setAllowgId(gradeCommand.getAllowgId());
		dto.setGradeId(gradeCommand.getGradeId());
		
		gradeMapper.allowgInsert(dto);
		
	}

}
