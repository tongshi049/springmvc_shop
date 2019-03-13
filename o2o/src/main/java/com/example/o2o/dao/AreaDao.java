package com.example.o2o.dao;

import com.example.o2o.entity.Area;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface AreaDao {
    /**
     * return area list
     * @return area list
     */
    List<Area> queryArea();
}
