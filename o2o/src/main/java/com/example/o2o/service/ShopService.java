package com.example.o2o.service;

import com.example.o2o.dto.ImageHolder;
import com.example.o2o.dto.ShopExecution;
import com.example.o2o.entity.Shop;
import com.example.o2o.exceptions.ShopOperationException;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public interface ShopService {

    public ShopExecution getShopList(Shop shopCondition, int pageIndex, int pageSize);

    ShopExecution addShop(Shop shop, ImageHolder thumbnail) throws ShopOperationException;

    Shop getByShopId(long shopId);

    ShopExecution modifyShop(Shop shop, ImageHolder thumbnail) throws ShopOperationException;

}
