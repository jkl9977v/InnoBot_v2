package com.innochatbot.admin.service.accessRule;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.innochatbot.admin.dto.AccessRuleDTO;
import com.innochatbot.admin.mapper.AccessRuleMapper;

@Service
public class AccessRuleListService {

    @Autowired
    AccessRuleMapper accessRuleMapper;

    public void ruleList(String access_id, Model model) {
        List<AccessRuleDTO> dto = accessRuleMapper.accessRuleSelectAll();

        model.addAttribute("dto", dto);

    }

}
