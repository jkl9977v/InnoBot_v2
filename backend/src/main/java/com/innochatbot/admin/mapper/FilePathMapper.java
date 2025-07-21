package com.innochatbot.admin.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.innochatbot.admin.dto.FilePathDTO;
import com.innochatbot.admin.dto.StartEndPageDTO;

@Mapper
public interface FilePathMapper {

    public List<FilePathDTO> filePathSelectAll(StartEndPageDTO dto);
    
    public Integer filePathCount(String pathId);

    public FilePathDTO filePathSelectId(String pathId);

    public void filePathInsert(FilePathDTO dto);

    public void filePathUpdate(FilePathDTO dto);

    public void filePathDelete(String pathId);

	public String selectPathId(String fullFilePath);



	
}
