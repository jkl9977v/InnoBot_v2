package com.innochatbot.admin.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.innochatbot.admin.dto.DepartmentDTO;
import com.innochatbot.admin.dto.StartEndPageDTO;

@Mapper
public interface DepartmentMapper { //allowd와 department
	
	//allowd

	public void allowdInsert(DepartmentDTO dto);

	public Integer allowdCount();

	public List<DepartmentDTO> allowdSelectAll(StartEndPageDTO dto);

	public DepartmentDTO allowdDetail(String allowdId);

	public void allowdUpdate(DepartmentDTO dto);

	public void allowdDelete(String allowdId);
	
	//department

	public void departmentInsert(DepartmentDTO dto);

	public Integer departmentCount();

	public List<DepartmentDTO> departmentSelectAll(StartEndPageDTO dto);

	public DepartmentDTO departmentDetail(String departmentId);

	public void departmentUpdate(DepartmentDTO dto);

	public void departmentDelete(String departmentId);

}
