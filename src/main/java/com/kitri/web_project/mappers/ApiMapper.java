package com.kitri.web_project.mappers;

import com.kitri.web_project.dto.api.BookMarks;
import com.kitri.web_project.dto.api.DataItem;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ApiMapper {

    List<DataItem> getNearBy(double latitude, double longitude);

    List<DataItem> getList(int page, String category, String city, String search);

    int getMaxPage(String category, String city, String search);

    void bookmarks(BookMarks bookMarks);
    void unbookmarks(Long bookMarks);

    BookMarks findBookmark(BookMarks bookMarks);
}
