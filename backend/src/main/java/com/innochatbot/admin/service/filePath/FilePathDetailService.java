package com.innochatbot.admin.service.filePath;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.innochatbot.admin.dto.FilePathDTO;
import com.innochatbot.admin.mapper.FilePathMapper;

@Service
public class FilePathDetailService {

    @Autowired

    FilePathMapper filePathMapper;

    public void pathDetail(String pathId, Model model) {
        FilePathDTO dto = filePathMapper.filePathSelectId(pathId);
        model.addAttribute("dto", dto);
    }
}
