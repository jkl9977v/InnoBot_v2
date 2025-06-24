package com.innochatbot.admin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.innochatbot.admin.mapper.AutoNumMapper;


@Service
public class AutoNumService {
    //select concat($column, nvl(max(substring(coloumn, 어디부터)),000000,+1) from table;

    @Autowired
    AutoNumMapper autoNumMapper;
    
    public String autoNum1(String column1, String column, int len, String table){
        String autoNum=autoNumMapper.autoNum1(column1, column, len, table);
        return autoNum;
    }

}
