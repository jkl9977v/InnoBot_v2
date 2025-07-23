package com.innochatbot.admin.service.allowd;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.innochatbot.admin.command.DepartmentCommand;
import com.innochatbot.admin.dto.DepartmentDTO;
import com.innochatbot.admin.mapper.DepartmentMapper;

@Service
public class AllowdUpdateService {
	@Autowired
	DepartmentMapper departmentMapper;

	public void allowdUpdate(DepartmentCommand departmentCommand) {
		DepartmentDTO dto = new DepartmentDTO();
		
		dto.setAllowdId(departmentCommand.getAllowdId());
		dto.setAllowdName(departmentCommand.getAllowdName());
		dto.setDepartmentId(departmentCommand.getDepartmentId());
		departmentMapper.allowdUpdate(dto);
	}

}
