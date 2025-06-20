package com.innochatbot.admin.service;

import com.innochatbot.admin.dto.ManualDTO;
import com.innochatbot.admin.mapper.ManualMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 📝 [쓰기 전용 서비스]
 * - 매뉴얼 등록
 * - 매뉴얼 수정
 * - 매뉴얼 삭제
 * - 파일 경로 업데이트
 */
@Service
public class ManualWriteService {
    /*
    @Autowired
    ManualMapper manualMapper;
    

    //새 매뉴얼 등록
    public void createManual(ManualDTO dto) {
        manualMapper.manualInsert(dto);
    }

    //기존 매뉴얼 수정
    public void updateManual(ManualDTO dto) {
        manualMapper.manualUpdate(dto);
    }

    //업로드된 파일의 경로를 DB에 저장
    public void updateFilePath(Long id, String filePath) {
        ManualDTO dto = manualMapper.manualSelectId(id); // 기존 매뉴얼 조회
        dto.setFilePath(filePath);                // 경로 설정
        manualMapper.manualUpdate(dto);                 // 다시 저장
    }
    */
}
