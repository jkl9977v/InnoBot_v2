package com.innochatbot.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("admin/grade")
public class GradeController {
	
	@GetMapping("gradeWrite")
	public String gradeWrite() {
		return "thymeleaf/grade/gradeWrite";
	}
	@PostMapping("gradeWrite")
	public String gradeWrite1() {
		return "redirect:/admin/grade/gradeList";
	}
	@GetMapping("gradeList")
	public String gradeList() {
		return "thymeleaf/grade/gradeList";
	}
	@GetMapping("gradeDetail")
	public String gradeDetail() {
		return "thymeleaf/grade/gradeDetail";
	}
	@GetMapping("gradeUpdate")
	public String gradeUpdate() {
		return "thyemleaf/grade/gradeUpadate";
	}
	@PostMapping("gradeUpdate")
	public String gradeUpdate1() {
		return "redirect:/admin/grade/gradeList";
	}
	@GetMapping("gradeDelete")
	public String gradeDelete() {
		return "redirect:/admin/grade/gradeDelete";
	}

}
