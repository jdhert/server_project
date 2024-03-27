package com.kitri.web_project.controller;

import com.kitri.web_project.dto.api.BookMarks;
import com.kitri.web_project.dto.api.DataItem;
import com.kitri.web_project.dto.api.SearchDto;
import com.kitri.web_project.service.ApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/{page}")
    public List<DataItem> getList(@PathVariable  int page, SearchDto searchDto){
        return apiService.getList(page, searchDto);
    }

    @PostMapping("/findBookmark")
    public boolean bookMarks(@RequestBody BookMarks bookMarks){
        return apiService.findBookmark(bookMarks) != null;
    }

    @PostMapping("/bookmarks")
    public boolean checkedBookmarks(@RequestBody BookMarks bookMarks){
        return apiService.checkedBookmarks(bookMarks);
    }
}
