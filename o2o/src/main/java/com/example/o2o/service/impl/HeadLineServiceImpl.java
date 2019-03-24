package com.example.o2o.service.impl;

import com.example.o2o.cache.JedisUtil;
import com.example.o2o.dao.HeadLineDao;
import com.example.o2o.entity.Area;
import com.example.o2o.entity.HeadLine;
import com.example.o2o.exceptions.AreaOperationException;
import com.example.o2o.exceptions.HeadLineOperationException;
import com.example.o2o.service.HeadLineService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class HeadLineServiceImpl implements HeadLineService {
    @Autowired
    private HeadLineDao headLineDao;

    @Autowired
    private JedisUtil.Keys jedisKeys;
    @Autowired
    private JedisUtil.Strings jedisStrings;

    private static Logger logger = LoggerFactory.getLogger(HeadLineServiceImpl.class);

    @Override
    public List<HeadLine> getHeadLineList(HeadLine headLineCondition) throws IOException {

        String key = HEADLINEKEY;
        List<HeadLine> headLineList = null;
        ObjectMapper mapper = new ObjectMapper();

        // distinct by enable status,
        if(headLineCondition!=null && headLineCondition.getEnableStatus() != null ){
            key = key +"_"+headLineCondition.getEnableStatus();
        }

        if(!jedisKeys.exists(key)){
            headLineList = headLineDao.queryHeadLine(headLineCondition);
            String jsonString = null;
            try {
                jsonString = mapper.writeValueAsString(headLineList);
            } catch (JsonProcessingException e){
                e.printStackTrace();
                logger.error(e.getMessage());
                throw new HeadLineOperationException(e.getMessage());
            }
            jedisStrings.set(key,jsonString);
        }else{
            String jsonString = jedisStrings.get(key);
            JavaType javaType = mapper.getTypeFactory().constructParametricType(ArrayList.class,HeadLine.class);
            try {
                headLineList = mapper.readValue(jsonString, javaType);
            } catch (IOException e) {
                e.printStackTrace();
                logger.error(e.getMessage());
                throw new HeadLineOperationException(e.getMessage());
            }
        }
        return headLineList;
    }
}
