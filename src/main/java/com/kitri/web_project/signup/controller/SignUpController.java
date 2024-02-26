package com.kitri.web_project.signup.controller;

import com.kitri.web_project.mybatis.mappers.UserMapper;
import com.kitri.web_project.signup.dto.RequestClient;
import com.kitri.web_project.signup.dto.ResponseUser;
import com.kitri.web_project.signup.dto.SignUpDto;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/signup")
public class SignUpController {

    @Autowired
    UserMapper userMapper;


    //회원가입
    @PostMapping("")
    public boolean signup(@RequestBody RequestClient requestClient, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return false;
        }

        String name = requestClient.getName();
        String email = requestClient.getEmail();
        String password = requestClient.getPassword();
        String password1 = requestClient.getPassword1();
        String address = requestClient.getAddress();


        ResponseUser responseUser = userMapper.findMember(name, email);
        if (responseUser != null) {
            return false;
        }

//        ArrayList<SignUpDto> members = (ArrayList<SignUpDto>) userMapper.memberInfo();
//        for (SignUpDto member : members) {
//            if (!(member.getEmail().equals(email)) && !(member.getName().equals(name)) && !(password.equals(password1))) {
//                return false;
//            }
//        }
        if(!(password.equals(password1))) {
            return false;
        }
        userMapper.signup(name, email, password, address);
        return true;
    }
}

