package com.innochatbot.admin.controller;

import com.innochatbot.admin.dto.ManualDTO;
import com.innochatbot.admin.service.ManualDeleteService;
import com.innochatbot.admin.service.ManualReadService;
import com.innochatbot.admin.service.ManualWriteService;

import ch.qos.logback.core.model.Model;

import com.innochatbot.admin.service.ManualFileService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/admin/manuals")
public class ManualController {

    @Autowired
    ManualReadService manualReadService;
    @Autowired
    ManualWriteService manualWriteService;
    @Autowired
    ManualDeleteService manualDeleteService;
    @Autowired
    ManualFileService manualFileService;


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

    @PostMapping("/admin/manuals/form")
    public String createManual(@ModelAttribute ManualDTO dto) {
        manualWriteService.createManual(dto);
        return "redirect:/admin/manuals/view";
    }

    // 📝 등록
    @PostMapping
    public ResponseEntity<Void> createManual(@RequestBody ManualDTO dto) {
        manualWriteService.createManual(dto);
        return ResponseEntity.ok().build();
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
}