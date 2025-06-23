package com.innochatbot.admin.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.innochatbot.admin.dto.FilePathDTO;

@Mapper
public interface FilePathMapper {
    
    public List<FilePathDTO> filePathSelectAll();

    public FilePathDTO filePathSelectId(Long id);

    public void filePathInsert(FilePathDTO filePath);

    public void filePathUpdate(FilePathDTO filePath);

    public void filePathDelete(Long id);
}
