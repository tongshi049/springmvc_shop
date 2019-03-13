package com.example.o2o.dao;

import com.example.o2o.BaseTest;
import com.example.o2o.entity.ProductCategory;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ProductCategoryDaoTest extends BaseTest {

    @Autowired
    private ProductCategoryDao productCategoryDao;

    @Test
    public void testBProductCategoryDao() {
        List<ProductCategory> list = productCategoryDao.queryProductCategoryList(1L);
        System.out.println(list.size());
        System.out.println(list.get(0).getProductCategoryName());
    }


    @Test
    public void testABatchInsertProductCategory() {
        ProductCategory productCategory = new ProductCategory();
        productCategory.setProductCategoryName("cat1");
        productCategory.setPriority(100);
        productCategory.setCreateTime(new Date());
        productCategory.setShopId(28L);

        ProductCategory productCategory2 = new ProductCategory();
        productCategory2.setProductCategoryName("cat2");
        productCategory2.setPriority(200);
        productCategory2.setCreateTime(new Date());
        productCategory2.setShopId(28L);

        List<ProductCategory> list = new ArrayList<>();
        list.add(productCategory);
        list.add(productCategory2);
        int effectedNum = productCategoryDao.batchInsertProductCategory(list);
        assertEquals(2, effectedNum);
    }

    @Test
    public void testCDeleteProductCategory() throws Exception {
        long shopId = 28L;
        List<ProductCategory> productCategories = productCategoryDao.queryProductCategoryList(shopId);
        for (ProductCategory pc: productCategories) {
            if ("cat1".equals(pc.getProductCategoryName()) || "cat2".equals(pc.getProductCategoryName())) {
                int effectedNum = productCategoryDao.deleteProductCategory(pc.getProductCategoryId(), shopId);
                assertEquals(1, effectedNum);
            }
        }
    }
}
