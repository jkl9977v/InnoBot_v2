package com.innochatbot.admin.service.accessRule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.innochatbot.admin.command.FilePathCommand;
import com.innochatbot.admin.dto.FilePathDTO;
import com.innochatbot.admin.mapper.FilePathMapper;

@Service
public class AccessRuleWriteService {

    @Autowired
    FilePathMapper filePathMapper;

    public void pathWrite(FilePathCommand filePathCommand) {
        FilePathDTO dto = new FilePathDTO();

        dto.setPathId(filePathCommand.getPathId());
        dto.setPath(filePathCommand.getPath());
        dto.setAccessId(filePathCommand.getAccessId());
        filePathMapper.filePathInsert(dto);
    }

}
