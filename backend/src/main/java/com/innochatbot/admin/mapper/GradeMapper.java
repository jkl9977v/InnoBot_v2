package com.innochatbot.admin.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.innochatbot.admin.dto.GradeDTO;

@Mapper
public interface GradeMapper {

	public void allowgInsert(GradeDTO dto);

}
