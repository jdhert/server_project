package com.kitri.web_project.preload;


import com.kitri.web_project.dto.api.ApiResponse;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

//@Service
public class DataService {

    private final List<ApiResponse> dataCache = new ArrayList<>(); // 위에서 미리 로딩한 데이터 캐시

    public void initializeDataCache(List<ApiResponse> data) {
        dataCache.addAll(data);
    }

    public void initializeDataCache2(List<ApiResponse> apiResponses) {
        dataCache.addAll(apiResponses);
    }

    // 필터링 메소드
//    public Flux<DataItem> getFilteredData(SomeCriteria criteria) {
//        return Flux.fromIterable(dataCache)
//                .filter(dataItem -> meetsCriteria(dataItem, criteria));
//    }
//
//    // 데이터 아이템이 주어진 조건을 만족하는지 검사
//    private boolean meetsCriteria(DataItem dataItem, SomeCriteria criteria) {
//        // 여기서 조건에 따라 필터링 로직을 구현
//        return true;
//    }
}
