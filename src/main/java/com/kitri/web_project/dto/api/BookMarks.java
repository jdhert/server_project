package com.kitri.web_project.dto.api;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookMarks {
    Long id;
    Long userId;
    Long placeId;
    Boolean checked;
}
