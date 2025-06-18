package com.innochatbot.admin.mapper;

import java.util.List;

import com.innochatbot.admin.dto.ManualDTO;


public interface ManualMapper {
    
    public List<ManualDTO> manualSelectAll();

    public ManualDTO manualSelectId(Long id);

    public void manualInsert(ManualDTO manual);

    public void manualUpdate(ManualDTO manual);

    public void manualDelete(Long id);
}
