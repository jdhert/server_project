package com.kitri.web_project.controller;

import com.kitri.web_project.dto.api.DataItem;
import com.kitri.web_project.service.ApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
    public List<DataItem> getLocate(double lat, double lon) {
        return apiService.getLocate(lat, lon);
    }

    @GetMapping("/category")
    public List<DataItem> getCategory(String category, int page){
        return apiService.getCategory(category, page);
    }

    @GetMapping("/{page}")
    public List<DataItem> getList(int page){
        return apiService.getList(page);
    }

//    @GetMapping
//    public void addEv() {
//        for(int i=1; i < 20; i++)
//            apiService.fetchAndSaveData(i, 225);
//        for(int j=20; j < 40; j++)
//            apiService.fetchAndSaveData(j, 225);
//        for(int j=40; j < 60; j++)
//            apiService.fetchAndSaveData(j, 225);
//        for(int j=60; j < 80; j++)
//            apiService.fetchAndSaveData(j, 225);
//        for(int j=80; j < 108; j++)
//            apiService.fetchAndSaveData(j, 225);
//
//    }

}
