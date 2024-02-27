package com.kitri.web_project.login.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseUser {

    @NotEmpty
    Long id;

    @NotNull
    String email;
    String password;
    String name;
    String address;
    String imgpath;
}
