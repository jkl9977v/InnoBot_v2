package com.innochatbot.admin.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface AutoNumMapper {

    public String autoNum1(@Param(value="cloumn1") String column1
                , @Param(value="column") String column
                , @Param(value="len") int len
                , @Param(value="table") String table);


    
}
