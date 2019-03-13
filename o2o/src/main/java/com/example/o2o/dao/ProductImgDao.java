package com.example.o2o.dao;

import com.example.o2o.entity.ProductImg;

import java.util.List;

public interface ProductImgDao {


    /**
     * list thumbnails of a product
     *
     * @param productId
     * @return
     */
    List<ProductImg> queryProductImgList(long productId);

    /**
     * batch insert product img
     *
     * @param productImgList
     * @return
     */
    int batchInsertProductImg(List<ProductImg> productImgList);

    /**
     * 删除指定商品下的所有详情图
     *
     * @param productId
     * @return
     */
    int deleteProductImgByProductId(long productId);
}

