package com.kitri.web_project.dto.board;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchDTO {
    String search;
    int offset;
    int limit;
}
