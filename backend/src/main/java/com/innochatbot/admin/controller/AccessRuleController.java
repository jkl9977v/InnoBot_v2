package com.innochatbot.admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.innochatbot.admin.command.AccessRuleCommand;
import com.innochatbot.admin.mapper.AccessRuleMapper;
import com.innochatbot.admin.service.AutoNumService;
import com.innochatbot.admin.service.accessRule.AccessRuleDetailService;
import com.innochatbot.admin.service.accessRule.AccessRuleListService;
import com.innochatbot.admin.service.accessRule.AccessRuleUpdateService;
import com.innochatbot.admin.service.accessRule.AccessRuleWriteService;


@RequestMapping("admin/accessRule")
@Controller
public class AccessRuleController { //파일 경로 관리
	@Autowired
	AutoNumService autoNumService;

    @Autowired
    AccessRuleWriteService accessRuleWriteService;
    @Autowired
    AccessRuleListService accessRuleListService;
    @Autowired
    AccessRuleUpdateService accessRuleUpdateService;
    @Autowired
    AccessRuleDetailService accessRuleDetailService;

    @GetMapping("ruleWrite") //경로 추가
    public String AccessRuleWrite(AccessRuleCommand accessRuleCommand
    		, @RequestParam(defaultValue = "rule_") String sep
    		, @RequestParam(defaultValue = "access_id") String column
    		, @RequestParam(defaultValue = "6") int len
    		, @RequestParam(defaultValue = "access_rule") String table) {
    	accessRuleCommand.setAccessId(autoNumService.autoNum1(sep, column,len, table));
        return "thymeleaf/accessRule/ruleWrite";
    }

    @PostMapping("ruleWrite")
    public String AccessRuleWrite1(AccessRuleCommand accessRuleCommand) {
        accessRuleWriteService.ruleWrite(accessRuleCommand);

        return "redirect:ruleList";
    }

    @GetMapping("ruleList")
    public String AccessRuleList( Model model) {
        accessRuleListService.ruleList( model);
        return "thymeleaf/accessRule/ruleList";
    }

    @GetMapping("ruleDetail")
    public String AccessRuleList1(@RequestBody String accessId, Model model) {
        accessRuleDetailService.ruleDetail(accessId, model);

        return "redirect:ruleList";
    }

    @GetMapping("ruleUpdate")
    public String AccessRuleUpdate(@RequestParam String param) {
        return "thymeleaf/accessRule/ruleUpdate";
    }

    @PostMapping("ruleUpdate")
    public String AccessRuleUpdate1(@RequestBody String entity) {
        //TODO: process POST request

        return "redirect:ruleList";
    }
    @Autowired
    AccessRuleMapper accessRuleMapper;

    @GetMapping("ruleDelete")
    public String AccessRuleDelete(@RequestParam String accessId) {
    	accessRuleMapper.accessRuleDelete(accessId);
        return "redirect:ruleList";
    }
}
