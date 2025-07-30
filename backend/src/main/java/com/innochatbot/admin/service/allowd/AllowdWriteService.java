package com.innochatbot.admin.service.allowd;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;

import com.innochatbot.admin.command.DepartmentCommand;
import com.innochatbot.admin.dto.DepartmentDTO;
import com.innochatbot.admin.mapper.DepartmentMapper;

@Service
//@Transactional
public class AllowdWriteService {
	@Autowired
	DepartmentMapper departmentMapper;

	public void allowdWrite(DepartmentCommand departmentCommand
			, List<String> departmentId) {
		for(String departmentId1 : departmentId) {
			DepartmentDTO dto = new DepartmentDTO();
			
			dto.setAllowdId(departmentCommand.getAllowdId());
			dto.setAllowdName(departmentCommand.getAllowdName());
			dto.setDepartmentId(departmentId1);
			departmentMapper.allowdInsert(dto);
			
			System.out.println("AllowDept Insert: "+departmentId1);
		}
	}
}
