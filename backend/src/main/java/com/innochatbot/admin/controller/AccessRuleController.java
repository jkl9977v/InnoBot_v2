package com.innochatbot.admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.innochatbot.admin.service.accessRule.AccessRuleDeleteService;
import com.innochatbot.admin.service.accessRule.AccessRuleListService;
import com.innochatbot.admin.service.accessRule.AccessRuleUpdateService;
import com.innochatbot.admin.service.accessRule.AccessRuleWriteService;

import ch.qos.logback.core.model.Model;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



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
    public ResponseEntity<ManualDTO> getManual(@access-ruleVariable Long id) {
        return ResponseEntity.ok(manualReadService.getManualById(id));
    }

    @GetMapping("/admin/manuals/form")
    public String showCreateForm(Model model) {
        model.addAttribute("manual", new ManualDTO());
        return "admin/manuals_form";
    }

    // 📝 수정
    @PutMapping("/{id}")
    public ResponseEntity<Void> updateManual(@access-ruleVariable Long id, @RequestBody ManualDTO dto) {
        //dto.setId(id);  // ID 명시
        manualWriteService.updateManual(dto);
        return ResponseEntity.ok().build();
    }

    // 🗑 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteManual(@access-ruleVariable Long id) {
        manualDeleteService.deleteManual(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/upload")
    public ResponseEntity<Void> uploadFile(@access-ruleVariable Long id,
                                       @RequestParam("file") MultipartFile file) throws IOException {
        String access-rule = manualFileService.store(file);             // 파일 저장
        manualWriteService.updateAccessRule(id, access-rule);             // DB에 경로 반영
        return ResponseEntity.ok().build();
    }
        */
}