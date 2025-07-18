package com.innochatbot.admin.service;

import org.springframework.stereotype.Service;

import com.innochatbot.admin.dto.StartEndPageDTO;

@Service
public class ListPageService {

	public StartEndPageDTO StartEndRow(int page, int limitRow, String filePath, String searchWord, String fullFilePath) {
		int startRow=(page-1)*limitRow-1;
		int endRow=startRow+limitRow-1;
		
		StartEndPageDTO dto = new StartEndPageDTO();
		dto.setStartRow(startRow);
		dto.setEndRow(endRow);
		dto.setFullFilePath(fullFilePath);
		dto.setSearchWord(searchWord);
		
		return dto;
		
	}

}
