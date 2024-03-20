package com.kitri.web_project.mappers;

import com.kitri.web_project.dto.*;
import com.kitri.web_project.dto.diary.DiaryImgDto;
import com.kitri.web_project.dto.diary.DiaryMainImg;
import com.kitri.web_project.dto.diary.RequestDiary;
import com.kitri.web_project.dto.diary.PetCalendar;
import com.kitri.web_project.dto.login.ResponseClient;
import com.kitri.web_project.dto.login.ResponseUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import javax.lang.model.element.NestingKind;
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
    RequestDiary petDiary(long id); //펫 다이어리 미리보기
    void deleteDiary(long diaryId); //다이어리 삭제하기
    List<PetCalendar> petCalendar(long id); //캘린더 미리보기
    void updateUser(UserUpdateInfo userUpdateInfo);
    String getUserImages(long id);
    List<RequestDiary>UpdateDiary(long id); //다이어리 수정 불러오기
    void editDiary(RequestDiary id); //다이어리 수정하기
    void deleteUser(Long id);
    void updatePassword(ResponseClient user);
    void updateNewPassword(List<Object> params);

    void imageSave(String imgPath, long userId, long petId, long id);

    List<DiaryImgDto> getDiaryImages(long id);
    void deleteImageById(@Param("id") long id);

    List<DiaryMainImg> diaryMainImages(long id);

//    List<String> diaryMainImages(long id);
}
