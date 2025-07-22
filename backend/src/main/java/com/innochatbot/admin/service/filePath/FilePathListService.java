package com.innochatbot.admin.service.filePath;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.innochatbot.admin.dto.FilePathDTO;
import com.innochatbot.admin.dto.StartEndPageDTO;
import com.innochatbot.admin.mapper.FilePathMapper;
import com.innochatbot.admin.service.ListPageService;

@Service
public class FilePathListService {

    @Autowired
    FilePathMapper filePathMapper;
    @Autowired
    ListPageService listPageService;


	public void filePathList(int page, int limitRow, String pathId, String searchWord, Model model) {
		
		//디렉토리의 파일, 폴더 목록 조회
		//String fullFilePath="D:/InnoBot_v3/"+filePath;
		//String pathId=filePathMapper.selectPathId(fullFilePath);
		
		
		StartEndPageDTO dto=listPageService.StartEndRow(page,limitRow, pathId, searchWord, null ); //kind=null;
		
		List<FilePathDTO> list = filePathMapper.filePathSelectAll(dto);
		Integer count = filePathMapper.filePathCount(pathId);
		
		listPageService.ShowList(page, limitRow, count,searchWord, list, model, pathId);
		
	}
	
// 이건 뭐 만드려고 짠건지 나도 잘 모르겠음
	// "/"안에 있는 경로 보여주는 코드??
//    public void pathList(String pathId, Model model) {
//        List<FilePathDTO> dto = filePathMapper.filePathSelectAll();
//        model.addAttribute("dto", dto);
//
//    }

}
