package com.kitri.web_project.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class DiaryInfo {
    Long id;
    String title;
    LocalDateTime createdAt;
    String content;
    Long petId;
    String petName;
}
