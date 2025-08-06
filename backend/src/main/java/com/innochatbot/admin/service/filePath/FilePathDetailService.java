package com.innochatbot.admin.service.filePath;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.innochatbot.admin.dto.AccessRuleDTO;
import com.innochatbot.admin.dto.FilePathDTO;
import com.innochatbot.admin.mapper.AccessRuleMapper;
import com.innochatbot.admin.mapper.FilePathMapper;

@Service
public class FilePathDetailService {

    @Autowired
    FilePathMapper filePathMapper;
    @Autowired
    AccessRuleMapper accessRuleMapper;

    public void pathDetail(String pathId, Model model) {
        FilePathDTO dto = filePathMapper.filePathDetail(pathId);
        AccessRuleDTO dto2 = accessRuleMapper.accessRuleDetail(dto.getAccessId());
        System.out.println(dto.getAccessId());
        System.out.println(dto2);
        model.addAttribute("dto", dto);
        model.addAttribute("dto2", dto2);
        
    }
}
