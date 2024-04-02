package com.kitri.web_project.controller;

import com.kitri.web_project.mappers.UserMapper;
import com.kitri.web_project.dto.login.RequestClient;
import com.kitri.web_project.dto.login.ResponseUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/signup")
public class SignUpController {

    @Autowired
    UserMapper userMapper;

    //회원가입
    @PostMapping
    public boolean signup(@RequestBody RequestClient requestClient, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return false;
        ResponseUser responseUser = userMapper.findMember(requestClient.getName(), requestClient.getEmail());
        if (responseUser != null)
            return false;
        else if(!(requestClient.getPassword().equals(requestClient.getPasswordVerify())))
            return false;
        else {
            userMapper.signup(requestClient.getEmail(), requestClient.getName(), requestClient.getPassword(), requestClient.getAddress(), requestClient.getImgPath(), false);
            return true;
        }
    }
}

