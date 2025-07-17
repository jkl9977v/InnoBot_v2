package com.innochatbot.admin.dto;

import java.sql.Date;

import org.apache.ibatis.type.Alias;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Alias("fileDTO")
@NoArgsConstructor
@AllArgsConstructor
public class FileDTO {

    String fileId;
    String fileName;
    String extenstion;
    String pathId;
    String hash;
    Date updateTime;
}
