package com.example.o2o.service;

import com.example.o2o.entity.HeadLine;

import java.io.IOException;
import java.util.List;

public interface HeadLineService {
    public static final String HEADLINEKEY = "headlineList";
    List<HeadLine> getHeadLineList(HeadLine headLineCondition) throws IOException;
}
