package com.kitri.web_project.dto.diary;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DiaryDto {
    String imgPath;
    long id;
    long diaryId;
    long petId;
    long userId;
}
