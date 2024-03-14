package com.kitri.web_project.controller;

import com.kitri.web_project.dto.DiaryInfo;
import com.kitri.web_project.dto.PetInfo;
import com.kitri.web_project.dto.UserUpdateInfo;
import com.kitri.web_project.dto.diary.RequestDiary;
import com.kitri.web_project.dto.diary.PetCalendar;
import com.kitri.web_project.dto.pet.UpdatePet;
import com.kitri.web_project.mybatis.mappers.UserMapper;
import com.kitri.web_project.dto.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/myinfo")
public class UserInfoController {

    @Autowired
    UserMapper userMapper;

    @GetMapping("/{id}")
    public UserInfo getInfo(@PathVariable String id) {
        long id1 = Long.parseLong(id);
        return userMapper.findById(id1);
    }

    @PutMapping
    public void updateUser(@RequestBody UserUpdateInfo userUpdateInfo) {
        userMapper.updateUser(userUpdateInfo);
    }

    @GetMapping("/img/{id}")
    public ResponseEntity<String> getUserImages(@PathVariable long id){
        String images = userMapper.getUserImages(id);
        if(images==null)
            return null;

        if (images.startsWith("http")) {
            return ResponseEntity.ok(images);
        } else {
            String imageUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/images/")
                    .path(images)
                    .toUriString();
            imageUrl = URLDecoder.decode(imageUrl, StandardCharsets.UTF_8); // URL 디코딩
            return ResponseEntity.ok(imageUrl);
        }
    }

    @GetMapping("/pet/{id}")
    public List<PetInfo> getPet(@PathVariable String id) {
        long id1 = Long.parseLong(id);
        return userMapper.getPets(id1);
    }

    @GetMapping("/diary/{id}")
    public List<DiaryInfo> getDiary(@PathVariable String id) {
        long id1 = Long.parseLong(id);
        return userMapper.getDiary(id1);
    }

    @PostMapping
    public void adddiary(@RequestBody RequestDiary diaryInfo) {
        userMapper.save(diaryInfo);
    }
    //다이어리 등록 매핑

    @GetMapping("/select/{id}")
    public List<RequestDiary>requestDiaries(@PathVariable long id){
//        long id1 = Long.parseLong(id);
        return userMapper.petDiary(id);
    }
    //다이어리 미리보기 매핑

    @DeleteMapping("/{diaryId}")
    public void RequestDiary(@PathVariable long diaryId) {
        userMapper.deleteDiary(diaryId);
        System.out.println("success");
    }

    @GetMapping("/calendar/{id}")
    public List<PetCalendar>petCalendars(@PathVariable long id){
        return userMapper.petCalendar(id);
    }
}
