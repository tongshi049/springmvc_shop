package com.example.o2o.service;

import com.example.o2o.dto.ImageHolder;
import com.example.o2o.dto.ProductExecution;
import com.example.o2o.entity.Product;
import com.example.o2o.exceptions.ProductOperationException;

import java.io.InputStream;
import java.util.List;

public interface ProductService {

    ProductExecution getProductList(Product productCondition, int pageIndex, int pageSize);

    Product getProductById(long productId);

    ProductExecution addProduct(Product product, ImageHolder thumbnail, List<ImageHolder> productImgList)
            throws ProductOperationException;

    ProductExecution modifyProduct(Product product, ImageHolder thumbnail, List<ImageHolder> productImages)
        throws ProductOperationException;

}
