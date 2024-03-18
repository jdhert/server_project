package com.kitri.web_project.dto.login;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestClient {

    @NotEmpty
    String name;

    @NotEmpty(message = "이메일 입력은 필수입니다.")
    @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+.[A-Za-z]{2,6}$", message = "이메일 형식에 맞지 않습니다.")
    String email;

    @NotEmpty(message = "비밀번호 입력은 필수입니다.")
    @Size(min = 8)
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{8,20}$")
    String password;

    @NotEmpty(message = "비밀번호 입력은 필수입니다.")
    @Size(min = 8)
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{8,20}$")
    String passwordVerify;

    String address;

}
