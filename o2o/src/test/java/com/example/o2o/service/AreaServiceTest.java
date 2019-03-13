package com.example.o2o.service;

import com.example.o2o.BaseTest;
import com.example.o2o.dao.AreaDao;
import com.example.o2o.entity.Area;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class AreaServiceTest extends BaseTest {
    @Autowired
    private AreaService areaService;

    @Test
    public void testGetAreaList() {
        List<Area> areaList = areaService.getAreaList();
        assertEquals("BC", areaList.get(0).getAreaName());
    }
}
