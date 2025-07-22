package com.innochatbot.admin.service.allowd;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.innochatbot.admin.command.DepartmentCommand;
import com.innochatbot.admin.dto.DepartmentDTO;
import com.innochatbot.admin.mapper.DepartmentMapper;

@Service
public class AllowdWriteService {
	@Autowired
	DepartmentMapper departmentMapper;

	public void allowdWrite(DepartmentCommand departmentCommand) {
		DepartmentDTO dto = new DepartmentDTO();
		
		dto.setAllowdId(departmentCommand.getAllowdId());
		dto.setDepartmentId(departmentCommand.getDepartmentId());
		//dto.setDepartmentName(departmentCommand.getDepartmentName());
		departmentMapper.allowdInsert(dto);
	}

}
