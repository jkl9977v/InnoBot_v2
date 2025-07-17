package com.innochatbot.admin.dto;

import org.apache.ibatis.type.Alias;

@Alias("loginDTO")
public class LoginDTO {
	String userId;
	String userPw;
}
