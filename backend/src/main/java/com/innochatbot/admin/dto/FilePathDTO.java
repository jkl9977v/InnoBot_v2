package com.innochatbot.admin.dto;

import org.apache.ibatis.type.Alias;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Alias("pathDTO")
@Setter
@Getter
public class FilePathDTO {

    String pathId;
    String path;
    String accessId;
}
