package com.kitri.web_project.mybatis.mappers;

import com.kitri.web_project.dto.api.DataItem;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ApiMapper {
    void insertIt(DataItem item);

    List<DataItem> getNearBy(double latitude, double longitude);

    List<DataItem> getByCategory(String category, int page);

    List<DataItem> getList(int page);
}
