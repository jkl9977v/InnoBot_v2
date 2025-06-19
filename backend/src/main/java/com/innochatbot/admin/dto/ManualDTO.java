package com.innochatbot.admin.dto;

import org.apache.ibatis.type.Alias;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Setter
@Getter
@Alias("ManualDTO")
public class ManualDTO { //회사 내부 매뉴얼 문서를 등록/조회/수정/삭제할 때 사용
    
    private Long id; //매뉴얼 고유 ID(기본키)
    private String title; //매뉴얼 파일명
    private String description; //매뉴얼 설명(요약 내용 등)
    private String filePath; //파일 경로

    public void setFilePath(String filePath) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
