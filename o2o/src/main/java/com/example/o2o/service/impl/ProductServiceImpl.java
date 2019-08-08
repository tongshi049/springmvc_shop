package com.example.o2o.service.impl;

import com.example.o2o.dao.ProductDao;
import com.example.o2o.dao.ProductImgDao;
import com.example.o2o.dto.ImageHolder;
import com.example.o2o.dto.ProductExecution;
import com.example.o2o.entity.Product;
import com.example.o2o.entity.ProductImg;
import com.example.o2o.enums.ProductStateEnum;
import com.example.o2o.exceptions.ProductOperationException;
import com.example.o2o.service.ProductService;
import com.example.o2o.util.ImageUtil;
import com.example.o2o.util.PageCalculator;
import com.example.o2o.util.PathUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductDao productDao;
    @Autowired
    private ProductImgDao productImgDao;

    @Override
    @Transactional
    /**
     * 1. deal with thumbnail, get relative directory of thumbnail and give it to product
     * 2. write data to tb_product, get productId
     * 3. according to productId, batch deal with thumbnails
     * 4. batch insert thumbnails into tb_product_img
     */
    public ProductExecution addProduct(Product product, ImageHolder thumbnail, List<ImageHolder> productImgHolderList) throws ProductOperationException {
        // empty value identity
        if (product != null && product.getShop() != null && product.getShop().getShopId() != null) {
            // set default attributes
            product.setCreateTime(new Date());
            product.setLastEditTime(new Date());
            // default on sale status
            product.setEnableStatus(1);
            // if thumbnail is not empty then add
            if (thumbnail != null)
                addThumbnail(product, thumbnail);
            try {
                // create product Info
                int effectedNum = productDao.insertProduct(product);
                if (effectedNum <= 0)
                    throw new ProductOperationException("creating product failed");
            } catch (Exception e) {
                throw new ProductOperationException("creating product failed" + e.toString());
            }
            // if thumbnail is not empty, then adding;
            if (productImgHolderList != null && productImgHolderList.size() > 0)
                addProductImgList(product, productImgHolderList);
            return new ProductExecution(ProductStateEnum.SUCCESS, product);
        } else {
            return new ProductExecution(ProductStateEnum.EMPTY);
        }
    }

    /**
     * add thumbnail
     */
    private void addThumbnail(Product product, ImageHolder thumbnail) {
        String dest = PathUtil.getShopImagePath(product.getShop().getShopId());
        String thumbnailAddr = ImageUtil.generateThumbnail(thumbnail, dest);
        product.setImgAddr(thumbnailAddr);
    }

    /**
     * batch add thumbnail
     */

    private void addProductImgList(Product product, List<ImageHolder> productImgHolderList) {
        // get image path, which is saved in the shop folder
        String dest = PathUtil.getShopImagePath(product.getShop().getShopId());
        List<ProductImg> productImgList = new ArrayList<>();
        for (ImageHolder imageHolder: productImgHolderList) {
            String imgAddr = ImageUtil.generateNormalImg(imageHolder, dest);
            ProductImg productImg = new ProductImg();
            productImg.setImgAddr(imgAddr);
            productImg.setProductId(product.getProductId());
            productImg.setCreateTime(product.getCreateTime());
            productImgList.add(productImg);
        }

        if (productImgList.size() > 0) {
            try {
                int effectedNum = productImgDao.batchInsertProductImg(productImgList);
                if (effectedNum <= 0)
                    throw new ProductOperationException("create product images failed!");
            } catch (Exception e) {
                throw new ProductOperationException("create product images failed!" + e.toString());
            }
        }
    }

    @Override
    public ProductExecution getProductList(Product productCondition, int pageIndex, int pageSize) {
        // pageIndex --> rowIndex in database, then get List
        int rowIndex = PageCalculator.calculateRowIndex(pageIndex, pageSize);
        List<Product> productList = productDao.queryProductList(productCondition, rowIndex, pageSize);
        int count = productDao.queryProductCount(productCondition);
        ProductExecution pe = new ProductExecution();
        pe.setProductList(productList);
        pe.setCount(count);
        return pe;
    }

    @Override
    public Product getProductById(long productId) {
        return productDao.queryProductById(productId);
    }

    @Override
    @Transactional
    /**
     * 1. if it has thumbnail, then dealing with thumbnail
     *    if existing thumbnail, first deleting it then adding new image,
     *    get relative directory of thumbnail give it to product
     * 2. deal with product image list
     * 3. clear logs in tb_product_img
     * 4. update tb_product_img, tb_product info
     */
    public ProductExecution modifyProduct(Product product, ImageHolder thumbnail,
           List<ImageHolder> productImgHolderList) throws ProductOperationException {
        if (product != null && product.getShop() != null && product.getShop().getShopId() != null) {
            // set default attributes of product
            product.setLastEditTime(new Date());
            // if thumbnail is not empty, then delete original thumbnail
            if (thumbnail != null) {
                // get original info at first
                Product tempProduct = productDao.queryProductById(product.getProductId());
                if (tempProduct.getImgAddr() != null) {
                    ImageUtil.deleteFileOrPath(tempProduct.getImgAddr());
                }
                addThumbnail(product, thumbnail);
            }
            if (productImgHolderList != null && productImgHolderList.size() > 0) {
                deleteProductImgList(product.getProductId());
                addProductImgList(product, productImgHolderList);
            }
            try {
                int effectedNum = productDao.updateProduct(product);
                if (effectedNum <= 0) {
                    throw new ProductOperationException("update product info failed");
                }
                return new ProductExecution(ProductStateEnum.SUCCESS, product);
            } catch (Exception e) {
                throw new ProductOperationException("update product info: " + e.toString());
            }
        } else {
            return new ProductExecution(ProductStateEnum.EMPTY);
        }
    }

    private void deleteProductImgList(long productId) {
        List<ProductImg> productImgList = productImgDao.queryProductImgList(productId);
        for (ProductImg productImg: productImgList) {
            ImageUtil.deleteFileOrPath(productImg.getImgAddr());
        }
        productImgDao.deleteProductImgByProductId(productId);
    }
}
