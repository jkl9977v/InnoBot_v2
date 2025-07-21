package com.innochatbot.admin.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.innochatbot.admin.dto.FilePathDTO;
import com.innochatbot.admin.dto.StartEndPageDTO;

@Service
public class ListPageService {

	public StartEndPageDTO StartEndRow(int page, int limitRow, String filePath, String searchWord, String fullFilePath) {
		int startRow=(page-1)*limitRow+1;
		int endRow=startRow+limitRow-1;
		
		StartEndPageDTO dto = new StartEndPageDTO();
		dto.setStartRow(startRow);
		dto.setEndRow(endRow);
		dto.setFullFilePath(fullFilePath);
		dto.setSearchWord(searchWord);
		
		return dto;
		
	}

	public void ShowList(int page, int limitRow, Integer count, String searchWord, List list,
			Model model) {
		Integer limitPage=10;
		Integer startPageNum=(int)((double)page/limitPage-0.05)*limitPage+1;
		Integer endPageNum=startPageNum+limitPage-1;
		Integer maxPageNum=(int)Math.ceil((double)count/limitRow);
		System.out.println("최대 페이지: "+maxPageNum);
		
		if(endPageNum>maxPageNum) endPageNum=maxPageNum;
		if(searchWord==null) searchWord="";
		//if(kind==null) kind="";
		model.addAttribute("page", page);
		model.addAttribute("startPageNum", startPageNum);
		model.addAttribute("endPageNum", endPageNum);
		model.addAttribute("maxPageNum", maxPageNum);
		model.addAttribute("limitRow", limitRow);
		model.addAttribute("searchWord", searchWord);
		model.addAttribute("list", list);
		model.addAttribute("count", count);
		//model.addAttribute("kind", kind);
		
	}

}
