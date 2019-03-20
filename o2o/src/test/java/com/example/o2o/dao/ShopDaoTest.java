package com.example.o2o.dao;

import com.example.o2o.BaseTest;
import com.example.o2o.entity.Area;
import com.example.o2o.entity.PersonInfo;
import com.example.o2o.entity.Shop;
import com.example.o2o.entity.ShopCategory;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ShopDaoTest extends BaseTest {

    @Autowired
    private ShopDao shopDao;

    @Test
    public void testQueryShopListAndCount() {
        Shop shopCondition = new Shop();
        ShopCategory childCategory = new ShopCategory();
        ShopCategory parentCategory = new ShopCategory();
        parentCategory.setShopCategoryId(12L);
        childCategory.setParent(parentCategory);
        shopCondition.setShopCategory(childCategory);
        List<Shop> shopList =shopDao.queryShopList(shopCondition,0,6);
        int count = shopDao.queryShopCount(shopCondition);
        System.out.println("shopList size :"+shopList.size());
        System.out.println("all count:" +count);
       /* PersonInfo owner = new PersonInfo();
        ShopCategory sc = new ShopCategory();
        sc.setShopCategoryId(3L);
        owner.setUserId(1L);
        shopCondition.setOwner(owner);
        shopCondition.setShopCategory(sc);
        List<Shop> shopList = shopDao.queryShopList(shopCondition, 0, 2);
        int count = shopDao.queryShopCount(shopCondition);
        System.out.println("Size of shopList: " + shopList.size());
        System.out.println("Total shop number: " + count);*/
    }

    @Test
    @Ignore
    public void testQueryByShopId() {
        long shopId = 1;
        Shop shop = shopDao.queryByShopId(shopId);
        System.out.println("areaName: " + shop.getArea().getAreaName());
        System.out.println("areaId: " + shop.getArea().getAreaId());
    }

    @Test
    @Ignore
    public void testUpdateShop() {
        Shop shop = new Shop();
        shop.setShopId(1L);
        PersonInfo owner = new PersonInfo();
        Area area = new Area();
        ShopCategory shopCategory = new ShopCategory();
        owner.setUserId((1L));

        shop.setShopDesc("test update desc");
        shop.setShopAddr("test update addr");
        shop.setLastEditTime(new Date());
        int effectedNum = shopDao.updateShop(shop);
        assertEquals(effectedNum, 1);
    }

    @Test
    @Ignore
    public void testInsertShop() {
        Shop shop = new Shop();
        PersonInfo owner = new PersonInfo();
        Area area = new Area();
        ShopCategory shopCategory = new ShopCategory();
        owner.setUserId((1L));
        area.setAreaId(2);
        shopCategory.setShopCategoryId(1L);
        shop.setOwner(owner);
        shop.setArea(area);
        shop.setShopCategory(shopCategory);
        shop.setShopName("tested shop");
        shop.setShopDesc("test");
        shop.setShopAddr("test");
        shop.setPhone("test");
        shop.setShopImg("test");
        shop.setCreateTime(new Date());
        shop.setEnableStatus(1);
        shop.setAdvice("under review");
        int effectedNum = shopDao.insertShop(shop);
        assertEquals(effectedNum, 1);
    }
}
