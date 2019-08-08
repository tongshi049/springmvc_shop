package com.example.o2o.dto;

import com.example.o2o.entity.Shop;
import com.example.o2o.enums.ShopStateEnum;

import java.util.List;

public class ShopExecution {

    // result state
    private int state;

    // state info (description of state)
    private String stateInfo;

    // shop numbers
    private int count;

    // Shop obj (which is used when doing Create, Update, Delete)
    private Shop shop;

    // shop list (used when doing Select)
    private List<Shop> shopList;

    public ShopExecution() {}

    // constructor when operations of shop are failed
    public ShopExecution(ShopStateEnum stateEnum) {
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
    }

    // constructor when operations of shop are successful
    public ShopExecution(ShopStateEnum stateEnum, Shop shop) {
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
        this.shop = shop;
    }

    // constructor of when operations of shop are successful
    public ShopExecution(ShopStateEnum stateEnum, List<Shop> shopList) {
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
        this.shopList = shopList;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public void setStateInfo(String stateInfo) {
        this.stateInfo = stateInfo;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }

    public List<Shop> getShopList() {
        return shopList;
    }

    public void setShopList(List<Shop> shopList) {
        this.shopList = shopList;
    }
}
