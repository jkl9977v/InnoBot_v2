package com.innochatbot.admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.innochatbot.admin.service.accessRule.AccessRuleDeleteService;
import com.innochatbot.admin.service.accessRule.AccessRuleDetailService;
import com.innochatbot.admin.service.accessRule.AccessRuleListService;
import com.innochatbot.admin.service.accessRule.AccessRuleUpdateService;
import com.innochatbot.admin.service.accessRule.AccessRuleWriteService;

@RestController
@RequestMapping("/admin/accessRule")

public class AccessRuleController { //파일 경로 관리

    @Autowired
    AccessRuleWriteService accessRuleWriteService;
    @Autowired
    AccessRuleListService accessRuleListService;
    @Autowired
    AccessRuleUpdateService accessRuleUpdateService;
    @Autowired
    AccessRuleDeleteService accessRuleDeleteService;
    @Autowired
    AccessRuleDetailService accessRuleDetailService;

    @GetMapping("ruleWrite") //경로 추가
    public String AccessRuleWrite(@RequestParam String param) {
        return "thymeleaf/admin/ruleWrite";
    }

    @PostMapping("ruleWrite")
    public String AccessRuleWrite1(@RequestBody String entity) {
        //TODO: process POST request

        return "redirect:ruleList";
    }

    @GetMapping("ruleList") //경로쪽 코드 복잡할 예정, 경로 목록 보여주기
    public String AccessRuleList(@RequestParam String access_id, Model model) {
        accessRuleListService.ruleList(access_id, model);
        return "thymeleaf/admin/ruleList";
    }

    @PostMapping("ruleList")
    public String AccessRuleList1(@RequestBody String entity) {
        //TODO: process POST request

        return "redirect:ruleList";
    }

    @GetMapping("ruleUpdate")
    public String AccessRuleUpdate(@RequestParam String param) {
        return "thymeleaf/admin/ruleUpdate";
    }

    @PostMapping("ruleUpdate")
    public String AccessRuleUpdate1(@RequestBody String entity) {
        //TODO: process POST request

        return "";
    }

    @GetMapping("ruleDelete")
    public String AccessRuleDelete(@RequestParam String param) {
        return "thymeleaf/admin/ruleDelete";
    }

    @PostMapping("ruleDelete")
    public String AccessRuleDelete1(@RequestBody String entity) {
        //TODO: process POST request

        return "redirect:ruleList";
    }
}
