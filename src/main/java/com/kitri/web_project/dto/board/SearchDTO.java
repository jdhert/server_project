package com.kitri.web_project.dto.board;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchDTO {
    String search;
    String type;
    int offset;
    int limit;
    int subject;
}
