package com.innochatbot.admin.dto;

import org.apache.ibatis.type.Alias;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Alias("pathDTO")
@AllArgsConstructor
@NoArgsConstructor
public class FilePathDTO {

    String pathId;
    String path;
    String accessId;
    int depth;
    String parentId;
}
