package com.innochatbot.admin.service.department;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.innochatbot.admin.dto.DepartmentDTO;
import com.innochatbot.admin.dto.StartEndPageDTO;
import com.innochatbot.admin.mapper.DepartmentMapper;
import com.innochatbot.admin.service.ListPageService;

@Service
public class DepartmentListService {

    private final WebMvcConfigurer corsConfigurer;
	@Autowired
	DepartmentMapper departmentMapper;
	@Autowired
	ListPageService listPageService;

    DepartmentListService(WebMvcConfigurer corsConfigurer) {
        this.corsConfigurer = corsConfigurer;
    }
	public void departmentList(int page, int limitPage, String searchWord, String kind, Model model) {
		//1. 
		StartEndPageDTO dto = listPageService.StartEndRow(page, limitPage, null, searchWord, kind);
		
		Integer count = departmentMapper.departmentCount();
		
		List<DepartmentDTO> list = departmentMapper.departmentSelectAll(dto);
		
	}
}
