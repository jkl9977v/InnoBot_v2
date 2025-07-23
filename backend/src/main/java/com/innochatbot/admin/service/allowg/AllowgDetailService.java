package com.innochatbot.admin.service.allowg;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.innochatbot.admin.dto.GradeDTO;
import com.innochatbot.admin.mapper.GradeMapper;

@Service
public class AllowgDetailService {
	@Autowired
	GradeMapper gradeMapper;

	public void allowgDetail(String allowgId, Model model) {
		GradeDTO dto = gradeMapper.allowgDetail(allowgId);
		model.addAttribute("dto", dto);
	}
}
