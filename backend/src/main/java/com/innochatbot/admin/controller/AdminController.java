package com.innochatbot.admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.innochatbot.admin.command.LoginCommand;
import com.innochatbot.admin.dto.ChatbotDTO;
import com.innochatbot.admin.dto.LoginDTO;
import com.innochatbot.admin.dto.UserDTO;
import com.innochatbot.admin.mapper.UserMapper;
import com.innochatbot.admin.service.ListPageService;
import com.innochatbot.admin.service.UserLoginService;
import com.innochatbot.admin.service.chatBot.ChatbotSettingService;
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
    
    @RequestMapping("")
    public String main(HttpServletRequest request) {
    	HttpSession session = request.getSession(false);
    	if(session != null && session.getAttribute("loginSession") != null) {
    		return "redirect:/admin/file";
    	}
    	return "redirect:/admin/login";
    }
    @RequestMapping("/")
    public String main1(HttpServletRequest request) {
    	HttpSession session = request.getSession(false);
    	if(session != null && session.getAttribute("loginSession") != null) {
    		return "redirect:/admin/file";
    	}
    	return "redirect:/admin/login";
    }
    @GetMapping("login")
    public String login() {
    	return "thymeleaf/auth-login";
    }
    @PostMapping("login")
    public String login1(LoginCommand loginCommand
    		, HttpSession session, HttpServletResponse response) {
    	//로그인 처리 과정
    	Boolean LoginStatus= userLoginService.adminLogin(loginCommand, session, response);
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
    public String adminMain() {
    	return "redirect:/admin/file/fileList";
    }
    @GetMapping("user")
    public String user() {
    	//유저 설정(유저 추가/부서/직급)
    	return "redirect:/admin/user/userList";
    }
    @GetMapping("accessRule") //접근권한 규칙 설정
    public String accessRule(
    		) {
        return "redirect:/admin/accessRule/accessList";
    }
    @Autowired
    UserMapper userMapper;
    @GetMapping("getHeader")
    public String getHeader(HttpServletResponse response, HttpSession session
    		, Model model) {
    	LoginDTO loginSession = (LoginDTO) session.getAttribute("loginSession");
    	if (loginSession != null) {
        	UserDTO dto = userMapper.userDetail(loginSession.getUserNum());
        	model.addAttribute("user", dto);
    	}else if (loginSession == null) {
    		model.addAttribute("user", null);
    	}
    	return "thymeleaf/getAll/getHeader";
    }
    @GetMapping("getMain2")
    public String getMain2() {
    	return "thymeleaf/getAll/getMain2";
    }
    @GetMapping("chatbot2")
    public String chatbot() {
    	return "thymeleaf/main";
    }
    @Autowired
    ChatbotSettingService chatbotSettingService;
    
    @GetMapping("chatbot-setting")
    public String chatbotSetting() {
    	return "thymeleaf/chatbot/chatbotSetting";
    }
    
    @PostMapping("chatbot-setting")
    @ResponseBody 
    public ResponseEntity<String> saveSetting(@RequestBody ChatbotDTO dto) {
    	chatbotSettingService.saveOrUpdate(dto);
        return ResponseEntity.ok("저장 완료");
    }


}
