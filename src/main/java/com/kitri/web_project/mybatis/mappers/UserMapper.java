package com.kitri.web_project.mybatis.mappers;

import com.kitri.web_project.dto.*;
import com.kitri.web_project.dto.diary.RequestDiary;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {

    ResponseUser findMember(String name, String email);
    void signup(String name, String email, String password, String address, String imgPath); //회원가입
    ResponseClient findByEmail(String email);
    UserInfo findById(long id);
    List<PetInfo> getPets(long id);
    List<DiaryInfo> getDiary(long id);
    void save(RequestDiary diaryInfo); //펫 다이어리 등록
    List<RequestDiary> petDiary(long id); //펫 다이어리 미리보기
}
