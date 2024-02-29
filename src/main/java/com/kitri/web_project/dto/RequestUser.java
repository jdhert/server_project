package com.kitri.web_project.dto;


import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestUser {

    @NotEmpty
    Long id;

}
