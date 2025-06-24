package com.innochatbot.admin.service.accessRule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.innochatbot.admin.command.AccessRuleCommand;
import com.innochatbot.admin.dto.AccessRuleDTO;
import com.innochatbot.admin.mapper.AccessRuleMapper;

@Service
public class AccessRuleUpdateService {

    @Autowired
    AccessRuleMapper accessRuleMapper;

    public void pathUpdate(AccessRuleCommand accessRuleCommand) {
        AccessRuleDTO dto = new AccessRuleDTO();

        dto.setPathId(accessRuleCommand.getPathId());
        dto.setPath(accessRuleCommand.getPath());
        dto.setAccessId(accessRuleCommand.getAccessId());
        accessRuleMapper.accessRuleInsert(dto);
    }

}
