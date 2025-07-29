package com.innochatbot.admin.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.innochatbot.admin.dto.GradeDTO;
import com.innochatbot.admin.dto.StartEndPageDTO;

@Mapper
public interface GradeMapper { //allowg, grade의 Mapper
	// allowg

	public void allowgInsert(GradeDTO dto);

	public Integer allowgCount();

	public List<GradeDTO> allowgSelectAll(StartEndPageDTO dto);

	public GradeDTO allowgDetail(String allowgId);

	public void allowgUpdate(GradeDTO dto);

	public void allowgDelete(String allowgId);
	
	// grade

	public void gradeInsert(GradeDTO dto);

	public Integer gradeCount(Integer gradeLevel);

	public List<GradeDTO> gradeSelectAll(@Param("dto") StartEndPageDTO dto
			, @Param("gradeLevel") Integer gradeLevel);

	public GradeDTO gradeDetail(String gradeId);

	public void gradeUpdate(GradeDTO dto);

	public void gradeDelete(String gradeId);

}
