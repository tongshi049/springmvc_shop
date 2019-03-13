package com.example.o2o.service;

import com.example.o2o.BaseTest;
import com.example.o2o.entity.ProductCategory;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ProductCategoryServiceTest extends BaseTest {

    @Autowired
    private ProductCategoryService productCategoryService;

    @Test
    public void TestProductCategoryService() {
        List<ProductCategory> list = productCategoryService.getProductCategoryList(1L);
        System.out.println(list.size());
        System.out.println(list.get(1).getProductCategoryName());
    }
}
