package com.example.o2o.service;

import com.example.o2o.entity.ShopCategory;

import java.util.List;

public interface ShopCategoryService {
    /**
     *  get the shop category list by shop category condition
     * @param shopCategoryCondition
     * @return
     */
    List<ShopCategory> getShopCategory(ShopCategory shopCategoryCondition);
}
