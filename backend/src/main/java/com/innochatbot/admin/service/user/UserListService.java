package com.innochatbot.admin.service.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.innochatbot.admin.dto.StartEndPageDTO;
import com.innochatbot.admin.dto.UserDTO;
import com.innochatbot.admin.mapper.UserMapper;
import com.innochatbot.admin.service.ListPageService;

import org.springframework.ui.Model;

@Service
public class UserListService {
	@Autowired
	UserMapper userMapper;
	@Autowired
	ListPageService listPageService;

	public void userList(int page, int limitRow, String searchWord, String kind, Model model) {
		//1.
		StartEndPageDTO dto = listPageService.StartEndRow(page, limitRow, null, searchWord, kind);
		
		//2.
		Integer count = userMapper.userCount();
		
		//3.
		List<UserDTO> list = userMapper.userSelectAll(dto);
		
		//4. 
		listPageService.ShowList(page, limitRow, count, searchWord, list, model, kind);
		
	}

}
