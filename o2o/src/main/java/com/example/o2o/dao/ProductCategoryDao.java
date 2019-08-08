package com.example.o2o.dao;

import com.example.o2o.entity.ProductCategory;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProductCategoryDao {
    /**
     * query shop_category_list by shop_id
     * @param shopId
     * @return
     */
    List<ProductCategory> queryProductCategoryList(@Param("shopId")Long shopId);

    int batchInsertProductCategory(List<ProductCategory> productCategoryList);

    int deleteProductCategory(@Param("productCategoryId") long productCategoryId, @Param("shopId") long shopId);

}
