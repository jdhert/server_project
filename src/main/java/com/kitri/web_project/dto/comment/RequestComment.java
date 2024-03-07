package com.kitri.web_project.dto.comment;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestComment {
    Long id;
    Long userId;
    String content;
}
