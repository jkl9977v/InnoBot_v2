package com.innochatbot.admin.service.filePath;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.innochatbot.admin.mapper.FilePathMapper;

@Service
public class FilePathDeleteService {

    @Autowired
    FilePathMapper filePathMapper;

    public void pathDelete(String path_id) {
        filePathMapper.filePathDelete(path_id);
    }
}
