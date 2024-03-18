package com.kitri.web_project.dto.api;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DataItem {
    private String 시설명;

    private String 위도;
    private String 경도;
    private String 지번주소;
    private double distance;
}
