package com.kitri.web_project.mybatis.mappers;

import com.kitri.web_project.login.dto.ResponseUser;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {

    String findAll(); //전체조회
    ResponseUser findByEmail(String email);

}


