package com.innochatbot.admin.service.filePath;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.innochatbot.admin.dto.FilePathDTO;
import com.innochatbot.admin.mapper.FilePathMapper;

@Service
public class FilePathListService {

    @Autowired
    FilePathMapper filePathMapper;

    public void pathList(String pathId, Model model) {
        List<FilePathDTO> dto = filePathMapper.filePathSelectAll();
        model.addAttribute("dto", dto);

    }

}
