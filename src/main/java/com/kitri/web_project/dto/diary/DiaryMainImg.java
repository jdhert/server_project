package com.kitri.web_project.dto.diary;

import lombok.Getter;
import lombok.Setter;
import org.eclipse.tags.shaded.org.apache.bcel.generic.StackInstruction;

import java.time.LocalDate;
import java.time.LocalDateTime;


@Getter
@Setter
public class DiaryMainImg {
    long id;
    long petId;
    long diaryId;
    String title;
    LocalDate createdAt;
    String content;
    String petName;
    String imgPath;
    private int maxPage;
    long diaryCount;
}
