package com.innochatbot.admin.dto;

import org.apache.ibatis.type.Alias;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Alias("allowGrade")
@AllArgsConstructor
@NoArgsConstructor
public class AllowGrade {
	String allowgId;
	String gradeId;
}
