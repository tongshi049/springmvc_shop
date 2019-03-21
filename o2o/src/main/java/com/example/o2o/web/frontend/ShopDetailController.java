package com.example.o2o.web.frontend;

import com.example.o2o.dto.ProductExecution;
import com.example.o2o.entity.Product;
import com.example.o2o.entity.ProductCategory;
import com.example.o2o.entity.Shop;
import com.example.o2o.service.ProductCategoryService;
import com.example.o2o.service.ProductService;
import com.example.o2o.service.ShopCategoryService;
import com.example.o2o.service.ShopService;
import com.example.o2o.util.HttpServletRequestUtil;
import org.apache.ibatis.jdbc.Null;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/frontend")
public class ShopDetailController {

    @Autowired
    private ShopService shopService;
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductCategoryService productCategoryService;

    /**
     *  get the shop info and all the product categories related to that shop
     * @param request
     * @return
     */
    @RequestMapping(value = "/listshopdetailpageinfo", method = RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> listShopDetailPageInfo(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        long shopId = HttpServletRequestUtil.getLong(request, "shopId");
        if (shopId == -1L) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "shopId required");
            return modelMap;
        }
        try {
            Shop shop = shopService.getByShopId(shopId);
            List<ProductCategory> productCategoryList = productCategoryService.getProductCategoryList(shopId);
            modelMap.put("shop", shop);
            modelMap.put("productCategoryList", productCategoryList);
            modelMap.put("success", true);
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.getMessage());
            return modelMap;
        }
        return modelMap;
    }

    /**
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/listproductsbyshop",method = RequestMethod.GET)
    @ResponseBody
    private Map<String,Object> listProductsByShop(HttpServletRequest request){
        Map<String, Object> modelMap = new HashMap<>();
        int pageIndex = HttpServletRequestUtil.getInt(request,"pageIndex");
        int pageSize = HttpServletRequestUtil.getInt(request,"pageSize");
        long shopId = HttpServletRequestUtil.getLong(request,"shopId");
        if(pageIndex == -1 || pageSize == -1 || shopId == -1L){
            modelMap.put("success",false);
            modelMap.put("errMsg","invalid parameter in Url");
            return modelMap;
        }
        long productCategoryId = HttpServletRequestUtil.getLong(request,"producateCategoryId");
        String productName = HttpServletRequestUtil.getString(request,"productName");
        Product productCondition = compactProductCondition4Search(shopId,productCategoryId,productName);
        ProductExecution pe = productService.getProductList(productCondition,pageIndex,pageSize);
        modelMap.put("productList",pe.getProductList());
        modelMap.put("count",pe.getCount());
        modelMap.put("success",true);
        return modelMap;
    }

    /**
     *
     * @param shopId
     * @param productCategoryId
     * @param productName
     * @return
     */
    private Product compactProductCondition4Search(long shopId, long productCategoryId, String productName) {
        Product productCondition = new Product();
        Shop shop = new Shop();
        shop.setShopId(shopId);
        productCondition.setShop(shop);
        if(productCategoryId != -1L){
            ProductCategory productCategory = new ProductCategory();
            productCategory.setProductCategoryId(productCategoryId);
            productCondition.setProductCategory(productCategory);
        }
        if(productName != null){
            productCondition.setProductName(productName);
        }
        productCondition.setEnableStatus(1);
        return productCondition;
    }
}
