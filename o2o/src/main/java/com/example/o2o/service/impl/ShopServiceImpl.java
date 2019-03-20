package com.example.o2o.service.impl;

import com.example.o2o.dao.ShopDao;
import com.example.o2o.dto.ImageHolder;
import com.example.o2o.dto.ShopExecution;
import com.example.o2o.entity.Shop;
import com.example.o2o.enums.ShopStateEnum;
import com.example.o2o.exceptions.ShopOperationException;
import com.example.o2o.service.ShopService;
import com.example.o2o.util.ImageUtil;
import com.example.o2o.util.PageCalculator;
import com.example.o2o.util.PathUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.awt.*;
import java.io.File;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

@Service
public class ShopServiceImpl implements ShopService {

    @Autowired
    private ShopDao shopDao;

    @Override
    public ShopExecution getShopList(Shop shopCondition, int pageIndex, int pageSize) {
        int rewIndex = PageCalculator.calculateRowIndex(pageIndex, pageSize);
        List<Shop> shopList = shopDao.queryShopList(shopCondition, rewIndex, pageSize);
        int count = shopDao.queryShopCount(shopCondition);
        ShopExecution se = new ShopExecution();
        if (!shopList.isEmpty()){
            se.setShopList(shopList);
            se.setCount(count);
        } else {
            se.setState(ShopStateEnum.INNER_ERROR.getState());
        }
        return se;
    }

    @Override
    @Transactional
    public ShopExecution addShop(Shop shop, ImageHolder thumbnail) throws ShopOperationException {

        // identify null value
        if (shop == null)
            return new ShopExecution(ShopStateEnum.NULL_SHOP);
        // TODO: other verification

        try {
            // give shop init info
            shop.setEnableStatus(0);
            shop.setCreateTime(new Date());
            shop.setLastEditTime(new Date());
            // add shop info
            int effectedNum = shopDao.insertShop(shop);
            if (effectedNum <= 0) {  // failed operation
                throw new ShopOperationException("shop creation fails");  // diff with Exception
            } else {
                if (thumbnail.getImage() != null) {
                    // save Img
                    try {
                        addShopImg(shop, thumbnail);
                    }catch (Exception e) {
                        throw new ShopOperationException("addShopImg error" + e.getMessage());
                    }
                    // update img addr of shop
                    effectedNum = shopDao.updateShop(shop);
                    if (effectedNum <= 0) {  // failed operation
                        throw new ShopOperationException("update shop img fails");  // diff with Exception
                    }
                }
            }
        } catch (Exception e) {
            throw new ShopOperationException("addShop error: " + e.getMessage());
        }
        return new ShopExecution(ShopStateEnum.CHECK, shop);
    }

    @Override
    public Shop getByShopId(long shopId) {
        return shopDao.queryByShopId(shopId);
    }

    @Override
    public ShopExecution modifyShop(Shop shop, ImageHolder thumbnail) throws ShopOperationException {

        if (shop == null || shop.getShopId() == null) {
            return new ShopExecution(ShopStateEnum.NULL_SHOP);
        } else {
            try {
                // 1. identify if we need to change img
                if (thumbnail.getImage() != null && thumbnail.getImageName() != null
                        || !"".equals(thumbnail.getImageName())) {
                    Shop tempShop = shopDao.queryByShopId(shop.getShopId());
                    if (tempShop.getShopImg() != null) {
                        ImageUtil.deleteFileOrPath(tempShop.getShopImg());
                    }
                    addShopImg(shop, thumbnail);
                }

                // 2. update shop info
                shop.setLastEditTime(new Date());
                int effectedNum = shopDao.updateShop(shop);
                if (effectedNum <= 0) {
                    return new ShopExecution(ShopStateEnum.INNER_ERROR);
                } else {
                    shop = shopDao.queryByShopId(shop.getShopId());
                    return new ShopExecution(ShopStateEnum.SUCCESS, shop);
                }
            } catch (Exception e) {
                throw new ShopOperationException("modifyShop error: " + e.getMessage());
            }
        }
    }

    private void addShopImg(Shop shop, ImageHolder thumbnail) {
        // Get Img relative path
        String dest = PathUtil.getShopImagePath(shop.getShopId());
        String shopImgAddr = ImageUtil.generateThumbnail(thumbnail, dest);
        shop.setShopImg(shopImgAddr);
    }
}
