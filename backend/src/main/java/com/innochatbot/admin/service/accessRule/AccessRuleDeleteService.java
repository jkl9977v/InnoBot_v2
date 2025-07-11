package com.innochatbot.admin.service.accessRule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.innochatbot.admin.mapper.AccessRuleMapper;

@Service
public class AccessRuleDeleteService {

    @Autowired
    AccessRuleMapper accessRuleMapper;

    public void pathDelete(String path_id) {
        accessRuleMapper.accessRuleDelete(path_id);
    }
}
