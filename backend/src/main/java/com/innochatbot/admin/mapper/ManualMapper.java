package com.innochatbot.admin.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.innochatbot.admin.dto.ManualDTO;

@Mapper
public interface ManualMapper {
    
    public List<ManualDTO> manualSelectAll();

    public ManualDTO manualSelectId(Long id);

    public void manualInsert(ManualDTO manual);

    public void manualUpdate(ManualDTO manual);

    public void manualDelete(Long id);
}
