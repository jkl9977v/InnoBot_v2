package com.innochatbot.admin.service.allowg;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.innochatbot.admin.command.GradeCommand;
import com.innochatbot.admin.dto.GradeDTO;
import com.innochatbot.admin.mapper.GradeMapper;

@Service
public class AllowgUpdateService {
	@Autowired
	GradeMapper gradeMapper;

	public void allowgUpdate(GradeCommand gradeCommand) {
		GradeDTO dto = new GradeDTO();
		
		dto.setAllowgId(gradeCommand.getAllowgId());
		dto.setAllowgName(gradeCommand.getAllowgName());
		dto.setGradeId(gradeCommand.getGradeId());
		//dto.setGradeName(gradeCommand.getGradeName());
		
		gradeMapper.allowgUpdate(dto);
	}
}
