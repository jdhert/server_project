package com.kitri.web_project.service;

import com.kitri.web_project.mappers.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class UserInfoService {
    @Autowired
    UserMapper userMapper;

    public boolean passwordVerify(long id, String password){
        String storedPassword = userMapper.passwordFind(id);
        if (storedPassword == null)
            return false;
        return Objects.equals(password, storedPassword);
    }
}
