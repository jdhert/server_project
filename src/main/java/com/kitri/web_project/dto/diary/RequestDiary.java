package com.kitri.web_project.dto.diary;

import lombok.Getter;
import lombok.Setter;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class RequestDiary {
    String name;
    String mood;
    String weather;
    String title;
    String content;
    long id;
    long userId;
    long petId;
    LocalDate createdAt;
    List<String> img;
}
