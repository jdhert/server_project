package com.kitri.web_project.controller;

import com.kitri.web_project.dto.DiaryInfo;
import com.kitri.web_project.dto.PetInfo;
import com.kitri.web_project.mybatis.mappers.UserMapper;
import com.kitri.web_project.dto.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/myinfo")
public class UserInfoController {

    @Autowired
    UserMapper userMapper;


    @GetMapping("/{id}")
    public UserInfo getInfo(@PathVariable String id){
        long id1 = Long.parseLong(id);
        return userMapper.findById(id1);
    }

    @GetMapping("/pet/{id}")
    public List<PetInfo> getPet(@PathVariable String id){
        long id1 = Long.parseLong(id);
        return userMapper.getPets(id1);
    }

    @GetMapping("/diary/{id}")
    public List<DiaryInfo> getDiary(@PathVariable String id){
        long id1 = Long.parseLong(id);
        return userMapper.getDiary(id1);
    }
//
//    @GetMapping("/")
//
//    @GetMapping("/")
//
//    @GetMapping("/")
//
//    @GetMapping("/")



}
