package com.innochatbot.admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.innochatbot.admin.service.UserLoginService;
import com.innochatbot.admin.service.filePath.FilePathListService;

@RequestMapping("admin")
@Controller
public class AdminController {

    @Autowired
    FilePathListService filePathListService;
    @Autowired
    UserLoginService userLoginService;
    
    @GetMapping("")
    public String login() {
    	return "thymeleaf/login";
    }
    @PostMapping("")
    public String login1(String userId, String userPw) {
    	//로그인 처리 과정
    	userLoginService.userLogin(userId, userPw);
    	return "redirect:/admin/file";
    }
    
    @GetMapping("file")
    public String adminMain() {
    	//파일시스템 보여줌
    	return "thymeleaf/file"; 
    }
    @GetMapping("user")
    public String user() {
    	//유저 설정(유저 추가/부서/직급)
    	return "redirtect:/admin/user/userList";
    }
    @GetMapping("accessRule") //접근권한 규칙 설정
    public String accessRule(
    		) {
        return "redirect:/admin/accessRule/ruleList";
    }
    //pageNo=1&pageSize=&searchText=&searchTextOption=userName&memberStatuses=CREATE

//    @GetMapping("filePath")
//    public String file_pathSetting(Model model) {
//        //디렉토리 조회
//        // model.addAttribute("file", List.of(
//        //     Map.of("id",1, "title", "A.LizardBackup"),
//        //     Map.of("id",2, "title","B.RansomeCruncher")
//        // ));
//        return "thymeleaf/admin/filePath"; //templates/admin/manuals.html
//    }
//


}
