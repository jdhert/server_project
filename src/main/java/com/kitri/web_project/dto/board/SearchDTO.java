package com.kitri.web_project.dto.board;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchDTO {
    String search;
    String type;
    String type1;
    int offset;
    int limit;
    int subject;
}
