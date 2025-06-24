package com.innochatbot.admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.innochatbot.admin.command.FilePathCommand;
import com.innochatbot.admin.service.AutoNumService;
import com.innochatbot.admin.service.filePath.FilePathDeleteService;
import com.innochatbot.admin.service.filePath.FilePathDetailService;
import com.innochatbot.admin.service.filePath.FilePathListService;
import com.innochatbot.admin.service.filePath.FilePathUpdateService;
import com.innochatbot.admin.service.filePath.FilePathWriteService;

@RestController
@RequestMapping("/admin/filePath")

public class FilePathController { //파일 경로 관리

    @Autowired
    AutoNumService autoNumService;

    @Autowired
    FilePathWriteService filePathWriteService;
    @Autowired
    FilePathListService filePathListService;
    @Autowired
    FilePathUpdateService filePathUpdateService;
    @Autowired
    FilePathDeleteService filePathDeleteService;

    @Autowired
    FilePathDetailService filePathDetailService;

    @GetMapping("pathWrite") //경로 추가
    public String filePathWrite(
            FilePathCommand filePathCommand,
            @RequestParam(defaultValue = "path") String column1,
            @RequestParam(defaultValue = "path_id") String column,
            @RequestParam(defaultValue = "5") int len,
            @RequestParam(defaultValue = "filePath") String table) {

        String pathId = autoNumService.autoNum1(column1, column, len, table);

        return "thymeleaf/admin/pathWrite";
    }

    @PostMapping("pathWrite")
    public String filePathWrite1(FilePathCommand filePathCommand) {
        filePathWriteService.pathWrite(filePathCommand);
        return "redirect:pathList";
    }

    @GetMapping("pathList") //경로쪽 코드 복잡할 예정, 경로 목록 보여주기
    public String filePathList(@RequestParam String path_id, Model model) {
        filePathListService.pathList(path_id, model);
        return "thymeleaf/admin/pathList";
    }

    @GetMapping("pathUpdate")
    public String filePathUpdate(@RequestParam String pathId, Model model) {
        filePathDetailService.pathDetail(pathId, model);
        return "thymeleaf/admin/pathUpdate";
    }

    @PostMapping("pathUpdate")
    public String filePathUpdate1(FilePathCommand filePathCommand) {
        filePathUpdateService.pathUpdate(filePathCommand);

        return "redirect:pathList";
    }

    @GetMapping("pathDelete")
    public String filePathDelete(@RequestParam String path_id) {
        filePathDeleteService.pathDelete(path_id);
        return "redirect:pathDelete";
    }
}
