package com.kitri.web_project.dto.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class DataItem {
    private long id;

    @JsonProperty("건물 번호")
    private String buildingNumber;

    @JsonProperty("경도")
    private String longitude;

//    @JsonProperty("기본 정보_장소설명")
    private String basicInfoPlaceDescription;

    @JsonProperty("도로명 이름")
    private String roadName;

    @JsonProperty("도로명주소")
    private String roadAddress;

    @JsonProperty("리 명칭")
    private String villageName;

//    @JsonProperty("반려동물 동반 가능정보")
    private String petCompanionInfo;

//    @JsonProperty("반려동물 전용 정보")
    private String petExclusiveInfo;

//    @JsonProperty("반려동물 제한사항")
    private String petRestriction;

    @JsonProperty("번지")
    private String addressNumber;

    @JsonProperty("법정읍면동명칭")
    private String jurisdictionTownName;

    @JsonProperty("시군구 명칭")
    private String districtName;

    @JsonProperty("시도 명칭")
    private String cityName;

    @JsonProperty("시설명")
    private String facilityName;

//    @JsonProperty("애견 동반 추가 요금")
    private String additionalPetFee;

    @JsonProperty("우편번호")
    private Integer postalCode;

    @JsonProperty("운영시간")
    private String operatingHours;

    @JsonProperty("위도")
    private String latitude;

//    @JsonProperty("입장 가능 동물 크기")
    private String petSizeAdmission;

//    @JsonProperty("입장(이용료)가격 정보")
    private String admissionFeeInfo;

//    @JsonProperty("장소(실내) 여부")
    private String indoorPlaceInfo;

//    @JsonProperty("장소(실외)여부")
    private String outdoorPlaceInfo;

    @JsonProperty("전화번호")
    private String phoneNumber;

//    @JsonProperty("주차 가능여부")
    private String parkingAvailability;

    @JsonProperty("지번주소")
    private String lotAddress;

    @JsonProperty("최종작성일")
    private String lastModifiedDate;

    @JsonProperty("카테고리1")
    private String category1;

    @JsonProperty("카테고리2")
    private String category2;

    @JsonProperty("카테고리3")
    private String category3;

    @JsonProperty("홈페이지")
    private String website;

    @JsonProperty("휴무일")
    private String dayOff;

    private double distance;

    private int maxPage=22955;
}
