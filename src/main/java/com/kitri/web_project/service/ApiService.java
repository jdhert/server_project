package com.kitri.web_project.service;

import com.kitri.web_project.dto.api.BookMarks;
import com.kitri.web_project.dto.api.DataItem;
import com.kitri.web_project.dto.api.SearchDto;
import com.kitri.web_project.mappers.ApiMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApiService {

    @Autowired
    private ApiMapper apiMapper;

    public List<DataItem> getLocate(double lat, double lon) {
        return apiMapper.getNearBy(lat, lon);
    }

    public List<DataItem> getList(int page, SearchDto searchDto) {
        List<DataItem> list = apiMapper.getList((page - 1) * 10, searchDto.getCategory(), searchDto.getCity(), searchDto.getSearch()+"%");
        if(list != null)
            list.get(0).setMaxPage((apiMapper.getMaxPage(searchDto.getCategory(), searchDto.getCity(), searchDto.getSearch()+"%")+9)/10);
        return list;
    }

    public void bookmarks(BookMarks bookMarks) {
        apiMapper.bookmarks(bookMarks);
    }
    public void unbookmarks(Long id) {
        apiMapper.unbookmarks(id);
    }
    public BookMarks findBookmark(BookMarks bookMarks) {
        return apiMapper.findBookmark(bookMarks);
    }
}
