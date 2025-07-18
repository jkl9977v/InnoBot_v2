package com.innochatbot.admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.innochatbot.admin.command.LoginCommand;
import com.innochatbot.admin.service.ListPageService;
import com.innochatbot.admin.service.UserLoginService;
import com.innochatbot.admin.service.filePath.FilePathListService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@RequestMapping("admin")
@Controller
public class AdminController {


    @Autowired
    FilePathListService filePathListService;
    @Autowired
    UserLoginService userLoginService;
    @Autowired
    ListPageService listPageService;
    
    @GetMapping("")
    public String main(HttpServletRequest request) {
    	HttpSession session = request.getSession(false);
    	if(session != null && session.getAttribute("loginSession") != null) {
    		return "redirect:/admin/file";
    	}
    	return "redirect:/admin/login";
    }
    @GetMapping("login")
    public String login() {
    	return "thymeleaf/login";
    }
    @PostMapping("")
    public String login1(LoginCommand loginCommand
    		, HttpSession session, HttpServletResponse response) {
    	//로그인 처리 과정
    	Boolean LoginStatus= userLoginService.userLogin(loginCommand, session, response);
    	if(LoginStatus) {
    		return "redirect:/admin/file";
    	}
    	else return "redirect:/admin/login";
    	
    }
    @GetMapping("logout")
    public String logout(HttpSession session) {
    	session.invalidate();
    	return "redirect:/admin";
    }
    
    
    
    @GetMapping("file")
    public String adminMain(@RequestParam (defaultValue="1") int page
    		, @RequestParam (defaultValue="10") int limitRow
    		, @RequestParam (defaultValue="docs")String filePath
    		, @RequestParam (required=false) String searchWord
    		, Model model) {
    	//파일시스템을 보여줌
    	filePathListService.filePathList(page, limitRow, filePath, searchWord, model);
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


}
