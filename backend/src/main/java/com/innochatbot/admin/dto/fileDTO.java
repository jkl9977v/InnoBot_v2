package com.innochatbot.admin.dto;

import java.sql.Date;

import org.apache.ibatis.type.Alias;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Alias("fileDTO")
@Setter
@Getter

public class fileDTO {

    String fileId;
    String fileName;
    String extenstion;
    String pathId;
    String hash;
    Date updateTime;
}
