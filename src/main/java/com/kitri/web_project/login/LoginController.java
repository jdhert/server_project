package com.kitri.web_project.login;

import com.kitri.web_project.login.dto.LoginUser;
import com.kitri.web_project.login.dto.ResponseClient;
import com.kitri.web_project.mybatis.mappers.UserMapper;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("/api/login")
public class LoginController {


    @Autowired
    UserMapper userMapper;

    @PostMapping("")
    public Long Log(@RequestBody LoginUser loginUser, HttpServletResponse response){
    ResponseClient responseUsers = userMapper.findByEmail(loginUser.getEmail());

//    String responseUsers =  userMapper.findAll();



        if(responseUsers == null)
            return 0L;

        if(!Objects.equals(loginUser.getPassword(), responseUsers.getPassword()))
            return  0L;

//        Cookie cookie = new Cookie("email", "pet4@pet.com");
//        cookie.setMaxAge(60*60*24*7);
//        response.addCookie(cookie);

        return responseUsers.getId();
}
}
