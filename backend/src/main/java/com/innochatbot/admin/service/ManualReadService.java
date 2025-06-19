package com.innochatbot.admin.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

import com.innochatbot.admin.dto.ManualDTO;
import com.innochatbot.admin.mapper.ManualMapper;

/**
 * 📘 [조회 전용 서비스]
 * - 매뉴얼 목록 조회
 * - 단건 매뉴얼 상세 조회
 */
public class ManualReadService {
    
    @Autowired
    ManualMapper manualMapper;


    public List<ManualDTO> manualSelectAll(){
        return manualMapper.manualSelectAll();
    }

    // ID로 특정 매뉴얼 1건 조회
    // @param id 매뉴얼 ID
    // @return 매뉴얼 DTO
     
    public ManualDTO getManualById(Long id) {
        return manualMapper.manualSelectId(id);
    }
    
}
