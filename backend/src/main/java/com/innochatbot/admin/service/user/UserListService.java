package com.innochatbot.admin.service.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.innochatbot.admin.dto.UserDTO;
import com.innochatbot.admin.mapper.UserMapper;

import org.springframework.ui.Model;

@Service
public class UserListService {
	@Autowired
	UserMapper userMapper;

	public void userList(Model model) {
		List<UserDTO> dto = userMapper.userSelectAll();
		model.addAttribute("dto", dto);
	}

}
