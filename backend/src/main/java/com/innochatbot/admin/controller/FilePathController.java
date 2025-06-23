package com.innochatbot.admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.innochatbot.admin.service.filePath.FilePathDeleteService;
import com.innochatbot.admin.service.filePath.FilePathListService;
import com.innochatbot.admin.service.filePath.FilePathUpdateService;
import com.innochatbot.admin.service.filePath.FilePathWriteService;

import ch.qos.logback.core.model.Model;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/admin/filePath")

public class FilePathController { //파일 경로 관리

    @Autowired
    FilePathWriteService filePathWriteService;
    @Autowired
    FilePathListService filePathListService;
    @Autowired
    FilePathUpdateService filePathUpdateService;
    @Autowired
    FilePathDeleteService filePathDeleteService;

    @GetMapping("pathWrite") //경로 추가
    public String filePathWrite(@RequestParam String param) {
        return "thymeleaf/admin/pathWrite";
    }

    @PostMapping("pathWrite")
    public String filePathWrite1(@RequestBody String entity) {
        //TODO: process POST request
        
        return "redirect:pathList";
    }
    

    @GetMapping("pathList") //경로쪽 코드 복잡할 예정, 경로 목록 보여주기
    public String filePathList(@RequestParam String path_id, Model model) {
        filePathListService.pathList(path_id, model);
        return "thymeleaf/admin/pathList";
    }

    @PostMapping("pathList")
    public String filePathList1(@RequestBody String entity) {
        //TODO: process POST request
        
        return "redirect:pathList";
    }
    
    @GetMapping("pathUpdate")
    public String filePathUpdate(@RequestParam String param) {
        return "thymeleaf/admin/pathUpdate";
    }

    @PostMapping("pathUpdate")
    public String filePathUpdate1(@RequestBody String entity) {
        //TODO: process POST request
        
        return "";
    }
    
    @GetMapping("pathDelete")
    public String filePathDelete(@RequestParam String param) {
        return "thymeleaf/admin/pathDelete";
    }

    @PostMapping("pathDelete")
    public String filePathDelete1(@RequestBody String entity) {
        //TODO: process POST request
        
        return "";
    }
    

    /* 
    // 📘 전체 목록 조회
    @GetMapping
    public ResponseEntity<List<ManualDTO>> getAllManuals() {
        return ResponseEntity.ok(manualReadService.manualSelectAll());
    }
    @GetMapping("view")
    public String viewManuals(ManualDTO manualDTO, Model model){
        List<ManualDTO> manuals = manualReadService.manualSelectAll();
        model.addAttribute("manuals", manuals);
        return "thymeleaf/admin/manuals";
    }

    // 📘 단건 조회
    @GetMapping("/{id}")
    public ResponseEntity<ManualDTO> getManual(@PathVariable Long id) {
        return ResponseEntity.ok(manualReadService.getManualById(id));
    }

    @GetMapping("/admin/manuals/form")
    public String showCreateForm(Model model) {
        model.addAttribute("manual", new ManualDTO());
        return "admin/manuals_form";
    }

    // 📝 수정
    @PutMapping("/{id}")
    public ResponseEntity<Void> updateManual(@PathVariable Long id, @RequestBody ManualDTO dto) {
        //dto.setId(id);  // ID 명시
        manualWriteService.updateManual(dto);
        return ResponseEntity.ok().build();
    }

    // 🗑 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteManual(@PathVariable Long id) {
        manualDeleteService.deleteManual(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/upload")
    public ResponseEntity<Void> uploadFile(@PathVariable Long id,
                                       @RequestParam("file") MultipartFile file) throws IOException {
        String path = manualFileService.store(file);             // 파일 저장
        manualWriteService.updateFilePath(id, path);             // DB에 경로 반영
        return ResponseEntity.ok().build();
    }
        */
}