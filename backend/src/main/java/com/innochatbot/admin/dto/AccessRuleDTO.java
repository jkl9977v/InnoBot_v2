package com.innochatbot.admin.dto;

import org.apache.ibatis.type.Alias;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Alias("accessRuleDTO")
@Setter
@Getter
public class AccessRuleDTO {

    String pathId;
    String path;
    String accessId;
}
