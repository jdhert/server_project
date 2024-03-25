package com.kitri.web_project.dto.login;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginUser {
    String email;
    String password;
    Boolean autologin;
}
