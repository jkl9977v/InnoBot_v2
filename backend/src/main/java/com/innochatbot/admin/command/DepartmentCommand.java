package com.innochatbot.admin.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class DepartmentCommand {
	String allowdId;
	String departmentId;
	String departmentName;
}
