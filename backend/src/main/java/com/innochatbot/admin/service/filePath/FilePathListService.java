package com.innochatbot.admin.service.filePath;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.innochatbot.admin.mapper.FilePathMapper;

import ch.qos.logback.core.model.Model;

@Service
public class FilePathListService {

    @Autowired
    FilePathMapper filePathMapper;
    
    public void pathList(String path_id, Model model) {
        
        throw new UnsupportedOperationException("Unimplemented method 'pathList'");
    }

}
