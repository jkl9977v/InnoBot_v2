package com.innochatbot.admin.controller;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@RequestMapping("admin")
@Controller
public class AdminController {
    
    @GetMapping("manuals")
    public String listManuals(Model model) {
        //manual 목록 조회
        model.addAttribute("manuals", List.of(
            Map.of("id",1, "title", "A.LizardBackup"),
            Map.of("id",2, "title","B.RansomeCruncher")
        ));
        return "thymeleaf/admin/manuals"; //templates/admin/manuals.html
    }

    @GetMapping("settings") // admin/settings
    public String listSettings(Model model) {
        //setting 목록 조회
        model.addAttribute("settings", List.of(
            Map.of("key", "timeout", "value","30"),
            Map.of("key","maxTokens","value", "500")
        ));
        return "thymeleaf/admin/settings"; 
    }
    
    
}
