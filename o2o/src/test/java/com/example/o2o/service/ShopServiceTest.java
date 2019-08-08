package com.example.o2o.service;

import com.example.o2o.BaseTest;
import com.example.o2o.dto.ImageHolder;
import com.example.o2o.dto.ShopExecution;
import com.example.o2o.entity.Area;
import com.example.o2o.entity.PersonInfo;
import com.example.o2o.entity.Shop;
import com.example.o2o.entity.ShopCategory;
import com.example.o2o.enums.ShopStateEnum;
import com.example.o2o.exceptions.ShopOperationException;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ShopServiceTest extends BaseTest {

    @Autowired
    private ShopService shopService;

    @Test
    public void testQueryShopListAndCount() {
        Shop shopCondition = new Shop();
        PersonInfo owner = new PersonInfo();
        ShopCategory sc = new ShopCategory();
        sc.setShopCategoryId(1L);
        shopCondition.setShopCategory(sc);
        ShopExecution se = shopService.getShopList(shopCondition, 3, 2);

        System.out.println("Size of shopList: " + se.getShopList().size());
        System.out.println("Total number: " + se.getCount());

    }

    @Test
    @Ignore
    public void testModifyShop() throws ShopOperationException, FileNotFoundException {
        Shop shop = new Shop();
        shop.setShopId(9L);
        shop.setShopName("new name");
        File shopImg = new File("C:\\codes\\img\\namei.jpg");
        InputStream is = new FileInputStream(shopImg);
        ImageHolder imageHolder = new ImageHolder("namei.jpg", is);
        ShopExecution shopExecution = shopService.modifyShop(shop, imageHolder);
        System.out.println(shopExecution.getShop().getShopImg());
    }

    @Test

    public void testAddShop() throws FileNotFoundException {
        Shop shop = new Shop();
        PersonInfo owner = new PersonInfo();
        Area area = new Area();
        ShopCategory shopCategory = new ShopCategory();
        owner.setUserId((1L));
        area.setAreaId(2);
        shopCategory.setShopCategoryId(33L);
        shop.setOwner(owner);
        shop.setArea(area);
        shop.setShopCategory(shopCategory);
        shop.setShopName("tested shop7");
        shop.setShopDesc("test7");
        shop.setShopAddr("test7");
        shop.setPhone("test7");
        shop.setCreateTime(new Date());
        shop.setEnableStatus(ShopStateEnum.CHECK.getState());
        shop.setAdvice("under review");
        File shopImg = new File("C:\\codes\\img\\namei.jpg");
        InputStream is = new FileInputStream(shopImg);
        ImageHolder imageHolder = new ImageHolder(shopImg.getName(), is);
        ShopExecution shopExecution = shopService.addShop(shop, imageHolder);
        assertEquals(ShopStateEnum.CHECK.getState(), shopExecution.getState());
    }
}
