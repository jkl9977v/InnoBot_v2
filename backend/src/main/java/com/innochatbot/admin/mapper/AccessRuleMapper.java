package com.innochatbot.admin.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.innochatbot.admin.dto.AccessRuleDTO;

@Mapper
public interface AccessRuleMapper {

    public List<AccessRuleDTO> accessRuleSelectAll();

    public AccessRuleDTO accessRuleSelectId(String pathId);

    public void accessRuleInsert(AccessRuleDTO dto);

    public void accessRuleUpdate(AccessRuleDTO dto);

    public void accessRuleDelete(String pathId);
}
