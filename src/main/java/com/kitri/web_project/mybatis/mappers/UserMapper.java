package com.kitri.web_project.mybatis.mappers;

import com.kitri.web_project.dto.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {


    List<SignUpDto> memberInfo(); //회원조회
    ResponseUser findMember(String name, String email);
    void signup(String name, String email, String password, String address, String imgPath); //회원가입
    String findAll(); //전체조회
    ResponseClient findByEmail(String email);
    UserInfo findById(long id);
    List<PetInfo> getPets(long id);
}
