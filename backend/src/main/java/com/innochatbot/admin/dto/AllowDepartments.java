package com.innochatbot.admin.dto;

import org.apache.ibatis.type.Alias;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Alias("allowDepartments")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AllowDepartments {
	String allowId;
	String departmentId;
}
