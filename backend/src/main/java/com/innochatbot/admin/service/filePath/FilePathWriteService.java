package com.innochatbot.admin.service.filePath;
import com.innochatbot.admin.service.UserLoginService;
import com.innochatbot.config.SecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.innochatbot.admin.command.FilePathCommand;
import com.innochatbot.admin.dto.FilePathDTO;
import com.innochatbot.admin.mapper.FilePathMapper;

@Service
public class FilePathWriteService {

    private final SecurityConfig securityConfig;

    private final UserLoginService userLoginService;

    @Autowired
    FilePathMapper filePathMapper;

    FilePathWriteService(UserLoginService userLoginService, SecurityConfig securityConfig) {
        this.userLoginService = userLoginService;
        this.securityConfig = securityConfig;
    }

    public void pathWrite(FilePathCommand filePathCommand) {
        FilePathDTO dto = new FilePathDTO();
        System.out.println(filePathCommand.getPathId());
        System.out.println(filePathCommand.getPath());
        System.out.println(filePathCommand.getAccessId());
        
        dto.setPathId(filePathCommand.getPathId());
        dto.setPath(filePathCommand.getPath());
        if(filePathCommand.getAccessId()=="") { //빈칸일때
        	dto.setAccessId(null);
        }else {
        	dto.setAccessId(filePathCommand.getAccessId());
        }
        
        filePathMapper.filePathInsert(dto);
    }

}
