package com.example.o2o.service;

import com.example.o2o.dao.ProductCategoryDao;
import com.example.o2o.dto.ProductCategoryExecution;
import com.example.o2o.entity.ProductCategory;
import com.example.o2o.exceptions.ProductCategoryOperationException;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public interface ProductCategoryService {

    List<ProductCategory> getProductCategoryList(Long shopId);

    ProductCategoryExecution batchAddProductCategory(List<ProductCategory> productCategoryList)
        throws ProductCategoryOperationException;

    /**
     * First set product_category_id in tb_product to be null, then delete this product category
     * @param productCategoryId
     * @param shopId
     * @return
     * @throws ProductCategoryOperationException
     */
    ProductCategoryExecution deleteProductCategory(long productCategoryId, long shopId)
            throws ProductCategoryOperationException;
}

