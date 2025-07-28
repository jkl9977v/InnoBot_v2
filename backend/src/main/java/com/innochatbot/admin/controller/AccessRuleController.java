package com.innochatbot.admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.innochatbot.admin.command.AccessRuleCommand;
import com.innochatbot.admin.command.DepartmentCommand;
import com.innochatbot.admin.command.GradeCommand;
import com.innochatbot.admin.mapper.AccessRuleMapper;
import com.innochatbot.admin.mapper.DepartmentMapper;
import com.innochatbot.admin.mapper.GradeMapper;
import com.innochatbot.admin.service.AutoNumService;
import com.innochatbot.admin.service.accessRule.AccessRuleDetailService;
import com.innochatbot.admin.service.accessRule.AccessRuleListService;
import com.innochatbot.admin.service.accessRule.AccessRuleUpdateService;
import com.innochatbot.admin.service.accessRule.AccessRuleWriteService;
import com.innochatbot.admin.service.allowd.AllowdDetailService;
import com.innochatbot.admin.service.allowd.AllowdListService;
import com.innochatbot.admin.service.allowd.AllowdUpdateService;
import com.innochatbot.admin.service.allowd.AllowdWriteService;
import com.innochatbot.admin.service.allowg.AllowgDetailService;
import com.innochatbot.admin.service.allowg.AllowgListService;
import com.innochatbot.admin.service.allowg.AllowgUpdateService;
import com.innochatbot.admin.service.allowg.AllowgWriteService;


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
    
    //1. accessRule 기본
    @GetMapping("ruleWrite") //경로 추가
    public String AccessRuleWrite(AccessRuleCommand accessRuleCommand
    		, @RequestParam(defaultValue = "rule_") String sep
    		, @RequestParam(defaultValue = "access_id") String column
    		, @RequestParam(defaultValue = "6") int len
    		, @RequestParam(defaultValue = "access_rule") String table
    		, Model model) {
    	accessRuleCommand.setAccessId(autoNumService.autoNum1(sep, column,len, table));
    	model.addAttribute("command", accessRuleCommand);
        return "thymeleaf/accessRule/ruleWrite";
    }

    @PostMapping("ruleWrite")
    public String AccessRuleWrite1(AccessRuleCommand accessRuleCommand) {
        accessRuleWriteService.ruleWrite(accessRuleCommand);
        return "redirect:ruleList";
    }

    @GetMapping("ruleList")
    public String AccessRuleList(@RequestParam (defaultValue="1") int page
    		, @RequestParam (defaultValue="10") int limitRow
    		, @RequestParam (required=false) String searchWord
    		, @RequestParam (required=false) String kind
    		, Model model) {
        accessRuleListService.ruleList(page, limitRow, searchWord,kind, model);
        return "thymeleaf/accessRule/ruleList";
    }

//    @GetMapping("ruleDetail")
//    public String AccessRuleList1(@RequestParam String accessId, Model model) {
//        accessRuleDetailService.ruleDetail(accessId, model);
//
//        return "thymeleaf/accessRule/ruleDetail";
//    }

    @GetMapping("ruleUpdate")
    public String AccessRuleUpdate(@RequestParam String accessId, Model model) {
    	accessRuleDetailService.ruleDetail(accessId, model);
        return "thymeleaf/accessRule/ruleUpdate";
    }

    @PostMapping("ruleUpdate")
    public String AccessRuleUpdate1(AccessRuleCommand accessRuleCommand) {
    	accessRuleUpdateService.ruleUpdate(accessRuleCommand);
        return "redirect:../accessRule";
    }
    
    @Autowired
    AccessRuleMapper accessRuleMapper;

    @GetMapping("ruleDelete")
    public String AccessRuleDelete(@RequestParam String accessId) {
    	accessRuleMapper.accessRuleDelete(accessId);
        return "redirect:../accessRule";
    }
    
    //rule 세부조건 설정하는 코드 작성하기
    //2. rule 세부조건 - allowd
    
    @Autowired
    AllowdWriteService allowdWriteService;
    @Autowired
    AllowdListService allowdListService;
    @Autowired
    AllowdDetailService allowdDetailService;
    @Autowired
    AllowdUpdateService allowdUpdateService;
    
    @GetMapping("allowdWrite")
    public String allowdWrite(DepartmentCommand departmentCommand
    		, @RequestParam(defaultValue = "allowd_") String sep
    		, @RequestParam(defaultValue = "allowd_id") String column
    		, @RequestParam(defaultValue = "8") int len
    		, @RequestParam(defaultValue = "allow_departments") String table
    		, Model model
    		) {
    	departmentCommand.setAllowdId(autoNumService.autoNum1(sep, column,len, table));
    	model.addAttribute("command", departmentCommand);
    	return "thymeleaf/allowDepartment/allowdWrite";
    }
    @PostMapping("allowdWrite")
    public String allowdWrite1(DepartmentCommand departmentCommand) {
    	allowdWriteService.allowdWrite(departmentCommand);
    	return "redirect:/admin/accessRule/allowdList";
    }
    @GetMapping("allowdList")
    public String allowdList(@RequestParam (defaultValue="1") int page
    		, @RequestParam (defaultValue="10") int limitRow
    		, @RequestParam (required=false) String searchWord
    		, @RequestParam (required=false) String kind
    		, Model model) {
    	allowdListService.allowdList(page, limitRow, searchWord, kind, model);
    	return "thymeleaf/allowDepartment/allowdList";
    }
    @GetMapping("allowdSearch")
    public String allowdSearch(@RequestParam (defaultValue="1") int page
    		, @RequestParam (defaultValue="10") int limitRow
    		, @RequestParam (required=false) String searchWord
    		, @RequestParam (required=false) String kind
    		, Model model) {
    	allowdListService.allowdList(page, limitRow, searchWord, kind, model);
    	return "thymeleaf/allowDepartment/allowdSearch";
    }
    @GetMapping("allowdDetail")
    public String allowdDetail(@RequestParam String allowdId, Model model) {
    	allowdDetailService.allowdDetail(allowdId, model);
    	return "thymeleaf/allowDepartment/allowdDetail";
    }
    @GetMapping("allowdUpdate")
    public String allowdUpdate(@RequestParam String allowdId, Model model) {
    	allowdDetailService.allowdDetail(allowdId, model);
    	return "thymeleaf/allowDepartment/allowdUpdate";
    }
    @PostMapping("allowdUpdate")
    public String allowdUpdate1(DepartmentCommand departmentCommand) {
    	allowdUpdateService.allowdUpdate(departmentCommand);
    	return "redirect:/admin/accessRule/allowdList";
    }
    @Autowired
    DepartmentMapper departmentMapper;
    @GetMapping("allowdDelte")
    public String allowdDelete(@RequestParam String allowdId) {
    	departmentMapper.allowdDelete(allowdId);
    	return "redirect:/admin/accessRule/allowdList";
    }
    
    
    //3. rule 세부조건 - allowg
    @Autowired
    AllowgWriteService allowgWriteService;
    @Autowired
    AllowgListService allowgListService;
    @Autowired
    AllowgDetailService allowgDetailService;
    @Autowired
    AllowgUpdateService allowgUpdateService;
    
    @GetMapping("allowgWrite")
    public String allowgWrite(GradeCommand gradeCommand
    		, @RequestParam(defaultValue = "allowg_") String sep
    		, @RequestParam(defaultValue = "allowg_id") String column
    		, @RequestParam(defaultValue = "8") int len
    		, @RequestParam(defaultValue = "allow_grade") String table
    		, Model model) {
    	gradeCommand.setAllowgId(autoNumService.autoNum1(sep, column, len, table));
    	model.addAttribute("command", gradeCommand);
    	return "thymeleaf//allowGrade/allowgWrite";
    }
    @PostMapping("allowgWrite")
    public String allowgWrite1(GradeCommand gradeCommand) {
    	allowgWriteService.allowgWrite(gradeCommand);
    	return "redirect:/admin/accessRule/allowgList";
    }
    @GetMapping("allowgList")
    public String allowgList(@RequestParam (defaultValue="1") int page
    		, @RequestParam (defaultValue="10") int limitRow
    		, @RequestParam (required=false) String searchWord
    		, @RequestParam (required=false) String kind
    		, Model model) {
    	allowgListService.allowgList(page, limitRow, searchWord, kind, model);
    	return "thymeleaf/allowGrade/allowgList";
    }
    @GetMapping("allowgSearch")
    public String allowgSearch(@RequestParam (defaultValue="1") int page
    		, @RequestParam (defaultValue="10") int limitRow
    		, @RequestParam (required=false) String searchWord
    		, @RequestParam (required=false) String kind
    		, Model model) {
    	allowgListService.allowgList(page, limitRow, searchWord, kind, model);
    	return "thymeleaf/allowGrade/allowgSearch";
    }
    @GetMapping("allowgDetail")
    public String allowgDetail(@RequestParam String allowgId, Model model) {
    	allowgDetailService.allowgDetail(allowgId, model);
    	return "thymeleaf/allowGrade/allowgDetail";
    }
    @GetMapping("allowgUpdate")
    public String allowgUpdate(@RequestParam String allowgId, Model model) {
    	allowgDetailService.allowgDetail(allowgId, model);
    	return "thymeleaf/allowGrade/allowgUpdate";
    }
    @PostMapping("allowgUpdate")
    public String allowgUpdate1(GradeCommand gradeCommand) {
    	allowgUpdateService.allowgUpdate(gradeCommand);
    	return "redirect:/admin/accessRule/allowgList";
    }
    @Autowired
    GradeMapper gradeMapper;
    
    @GetMapping("allowgDelte")
    public String allowgDelete(@RequestParam String allowgId) {
    	gradeMapper.allowgDelete(allowgId);
    	return "redirect:/admin/accessRule/allowgList";
    }
    
}
