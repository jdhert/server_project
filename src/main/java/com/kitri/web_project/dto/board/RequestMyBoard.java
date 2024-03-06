package com.kitri.web_project.dto.board;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestMyBoard {
    Long id;
    int subject;
    int offset;
    int limit;
}
