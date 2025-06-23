package com.innochatbot.admin.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.innochatbot.admin.service.filePath.FilePathListService;
import org.springframework.web.bind.annotation.RequestParam;



@RequestMapping("admin")
@Controller
public class AdminController {
    @Autowired
    FilePathListService filePathListService;
    
    @GetMapping("filePath")
    public String file_pathSetting(Model model) {
        //디렉토리 조회
        model.addAttribute("file", List.of(
            Map.of("id",1, "title", "A.LizardBackup"),
            Map.of("id",2, "title","B.RansomeCruncher")
        ));
        return "thymeleaf/admin/filePath"; //templates/admin/manuals.html
    }

    @GetMapping("accessRule") //접근권한 규칙 설정
    public String accessRule(@RequestParam String param) {
        return "thymeleaf/admin/accessRule";
    }
    
}
