package com.example.o2o.dao;

import com.example.o2o.entity.Shop;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ShopDao {

    /**
     * query shops according to : shopName (fuzzy), shop status, shop category, areaId, owner
     * @param rowIndex: from which row to get data.
     * @param pageSize : return number of rows
     * @return
     */
    List<Shop> queryShopList(@Param("shopCondition") Shop shopCondition, @Param("rowIndex") int rowIndex,
                             @Param("pageSize") int pageSize); // multiple params, we must identify param Name

    /**
     * Return total shop number
     * @param shopCondition
     * @return
     */

    int queryShopCount(@Param("shopCondition") Shop shopCondition);

    /**
     * Query the shop according to shop id
     */
    Shop queryByShopId(long shopId);

    /**
     * register new shop
     * @param shop
     * @return
     */
    int insertShop(Shop shop);

    /**
     * update shop info
     * @param shop
     * @return
     */
    int updateShop(Shop shop);
}
