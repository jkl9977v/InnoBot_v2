package com.innochatbot.api.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.web.bind.annotation.RequestParam;

@Mapper
public interface FileMapper {

	public String filehashSelect(String fileId);

	public String fileIdSelect(@RequestParam("fileName") String fileName
			,@RequestParam("path") String path);

	public void updateFileHash(@RequestParam("newHash") String newHash
			,@RequestParam("fileId") String fileId);

}
