package com.kitri.web_project.dto.pet;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class getPetDiary {
    long petId;
    long diaryId;
    String title;
    LocalDate createdAt;
    String imgPath;
    String petName;

}
