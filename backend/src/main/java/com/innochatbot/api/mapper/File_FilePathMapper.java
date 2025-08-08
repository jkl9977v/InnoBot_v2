package com.innochatbot.api.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.web.bind.annotation.RequestParam;

@Mapper
public interface File_FilePathMapper {

	public String filehashSelect(String fileId);

	public List<String> fileIdSelect(@RequestParam("fileName") String fileName
			,@RequestParam("path") String path);

}
