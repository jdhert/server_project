package com.kitri.web_project.dto.image;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestImage {
    long id;
    long boardId;
    String imagePath;
}
