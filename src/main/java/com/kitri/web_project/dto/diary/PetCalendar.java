package com.kitri.web_project.dto.diary;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class PetCalendar {
    String name;
    LocalDate createdAt;
}

