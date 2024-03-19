package com.kitri.web_project.controller;

import com.kitri.web_project.dto.api.DataItem;
import com.kitri.web_project.service.ApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/api/data")
public class ApiController {
    private final ApiService apiService;

    @Autowired
    public ApiController(ApiService apiService){
        this.apiService = apiService;
    }

    @GetMapping("/locate")
    public Mono<List<DataItem>> getLocate(int page, int perPage, double lon, double lat) {
        return apiService.getNearestLocations(lon, lat , page, perPage);
    }

//    @GetMapping("/catgry")
//    public Mono<List<DataItem>> getCategory(String input, int page, int perPage){
//        return apiService.getCategoryItem(input, page, perPage);
//    }
}
