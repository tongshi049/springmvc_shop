package com.example.o2o.web.shopadmin;

import com.example.o2o.dto.ImageHolder;
import com.example.o2o.dto.ShopExecution;
import com.example.o2o.entity.*;
import com.example.o2o.enums.ShopStateEnum;
import com.example.o2o.service.AreaService;
import com.example.o2o.service.ProductCategoryService;
import com.example.o2o.service.ShopCategoryService;
import com.example.o2o.service.ShopService;
import com.example.o2o.util.CodeUtil;
import com.example.o2o.util.HttpServletRequestUtil;
import com.example.o2o.util.ImageUtil;
import com.example.o2o.util.PathUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/shopadmin")
public class ShopManagementController {

    @Autowired
    private ShopService shopService;
    @Autowired
    private ShopCategoryService shopCategoryService;
    @Autowired
    private AreaService areaService;
    @Autowired
    private ProductCategoryService productCategoryService;

    /*@RequestMapping(value = "/getproductcategorylist", method = RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> getProductCategory(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();

        Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
        if (currentShop == null) {
            modelMap.put("redirect", true);
            modelMap.put("url", "/o2o/shopadmin/shoplist");
            return modelMap;
        }
        Long shopId = currentShop.getShopId();
        try {
            List<ProductCategory> list = productCategoryService.getProductCategory(shopId);
            modelMap.put("productCategoryList", list);
            modelMap.put("success", true);
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.getMessage());
        }
        return modelMap;
}*/

    @RequestMapping(value = "/getshopmanagementinfo", method = RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> getShopManagementInfo(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        long shopId = HttpServletRequestUtil.getLong(request, "shopId");
        if (shopId <= 0) {
            Object currentShopObj = request.getSession().getAttribute("currentShop");
            if (currentShopObj == null) {
                modelMap.put("redirect", true);
                modelMap.put("url", "/o2o/shopadmin/shoplist");
            } else {
                Shop currentShop = (Shop) currentShopObj;
                modelMap.put("redirect", false);
                modelMap.put("shopId", currentShop.getShopId());
            }
        } else {
            Shop currentShop = new Shop();
            currentShop.setShopId(shopId);
            request.getSession().setAttribute("currentShop", currentShop);
            modelMap.put("redirect", false);
        }
        return modelMap;
    }

    @RequestMapping(value = "/getshoplist", method = RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> getShopList(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        PersonInfo user = new PersonInfo();
        user.setUserId(1L);
        user.setName("Ashe");
        request.getSession().setAttribute("user", user);
        user = (PersonInfo) request.getSession().getAttribute("user");
        try {
            Shop shopCondition = new Shop();
            shopCondition.setOwner(user);
            ShopExecution se = shopService.getShopList(shopCondition, 0, 100);
            modelMap.put("shopList", se.getShopList());
            modelMap.put("user", user);
            modelMap.put("success", true);
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.getMessage());
        }
        return modelMap;
    }

    @RequestMapping(value = "/modifyshop", method = RequestMethod.POST)
    @ResponseBody
    private Map<String, Object> modifyShop(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        if (!CodeUtil.checkVerifyCode(request)) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "wrong verify code");
            return modelMap;
        }
        // 1. receive and process parameters, including shop info and img info
        String shopStr = HttpServletRequestUtil.getString(request, "shopStr"); //f-e
        ObjectMapper mapper = new ObjectMapper();
        Shop shop = null;
        try {
            shop = mapper.readValue(shopStr, Shop.class);
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.getMessage());
            return modelMap;
        }
        CommonsMultipartFile shopImg = null;
        CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver(
                request.getSession().getServletContext());
        if (commonsMultipartResolver.isMultipart(request)) {
            MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
            shopImg = (CommonsMultipartFile) multipartHttpServletRequest.getFile("shopImg"); //f-e
        }
        // 2. modify shop info
        if (shop != null && shop.getShopId() != null) {
            ShopExecution se = null;
            try {
                if (shopImg == null) {
                    se = shopService.modifyShop(shop, null);
                }
                ImageHolder imageHolder = new ImageHolder(shopImg.getOriginalFilename(), shopImg.getInputStream());
                se = shopService.addShop(shop, imageHolder);
                if (se.getState() == ShopStateEnum.SUCCESS.getState()) {
                    modelMap.put("success", true);
                } else {
                    modelMap.put("success", false);
                    modelMap.put("errMsg", se.getStateInfo());
                }
            } catch (IOException e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", e.getMessage());
            }
            return modelMap;
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "please input shop id");
            return modelMap;
        }
    }


    @RequestMapping(value = "/getshopbyid", method = RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> getShopById(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        Long shopId = HttpServletRequestUtil.getLong(request, "shopId");
        if (shopId > -1) {
            try {
                Shop shop = shopService.getByShopId(shopId);
                List<Area> areaList = areaService.getAreaList();
                modelMap.put("shop", shop);
                modelMap.put("areaList", areaList);
                modelMap.put("success", true);
            } catch (Exception e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", e.toString());
            }
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "empty shopId");
        }
        return modelMap;
    }


    @RequestMapping(value = "/getshopinitinfo", method = RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> getShopInitInfo() {
        Map<String, Object> modelMap = new HashMap<>();
        List<ShopCategory> shopCategoryList = new ArrayList<>();
        List<Area> areaList = new ArrayList<>();
        try {
            shopCategoryList = shopCategoryService.getShopCategoryList(new ShopCategory());
            areaList = areaService.getAreaList();
            modelMap.put("shopCategoryList", shopCategoryList);
            modelMap.put("areaList", areaList);
            modelMap.put("success", true);
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.getMessage());
        }

        return modelMap;
    }

    @RequestMapping(value = "/registershop", method = RequestMethod.POST)
    @ResponseBody
    private Map<String, Object> registerShop(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        if (!CodeUtil.checkVerifyCode(request)) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "wrong verify code");
            return modelMap;
        }
        // 1. receive and process parameters, including shop info and img info
        String shopStr = HttpServletRequestUtil.getString(request, "shopStr"); //f-e
        ObjectMapper mapper = new ObjectMapper();
        Shop shop = null;
        try {
            shop = mapper.readValue(shopStr, Shop.class);
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.getMessage());
            return modelMap;
        }
        CommonsMultipartFile shopImg = null;
        CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver(
                request.getSession().getServletContext());
        if (commonsMultipartResolver.isMultipart(request)) {
            MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
            shopImg = (CommonsMultipartFile) multipartHttpServletRequest.getFile("shopImg"); //f-e
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "uploaded img cannot be empty");
            return modelMap;
        }
        // 2. register shop
        if (shop != null && shopImg != null) {
            PersonInfo owner = (PersonInfo) request.getSession().getAttribute("user");
            shop.setOwner(owner);
            ShopExecution se = null;
            try {
                ImageHolder imageHolder = new ImageHolder(shopImg.getOriginalFilename(), shopImg.getInputStream());
                se = shopService.addShop(shop, imageHolder);
                if (se.getState() == ShopStateEnum.CHECK.getState()) {
                    modelMap.put("success", true);
                    // list consisting of shops that current user can operate
                    List<Shop> shopList = (List<Shop>) request.getSession().getAttribute("shopList");
                    if (shopList == null || shopList.size() == 0) {
                        shopList = new ArrayList<>();
                    }
                    shopList.add(se.getShop());
                    request.getSession().setAttribute("shopList", shopList);
                } else {
                    modelMap.put("success", false);
                    modelMap.put("errMsg", se.getStateInfo());
                }
            } catch (IOException e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", e.getMessage());
            }
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "please input shop info");
        }
        // 3. return result
        return modelMap;
    }

    /*private static void inputStreamToFile(InputStream ins, File file) {
        FileOutputStream os = null;
        try {
            os = new FileOutputStream(file);
            int byteRead = 0;
            byte[] buffer = new byte[1024];
            while ((byteRead = ins.read(buffer)) != -1) {
                os.write(buffer, 0, byteRead);
            }
        } catch (Exception e) {
            throw new RuntimeException("call inputStreamToFile Exception" + e.getMessage());
        } finally {
            try {
                if (os != null)
                    os.close();
                if (ins != null)
                    ins.close();
            } catch (IOException e) {
                throw new RuntimeException("Exception when close IO streams" + e.getMessage());
            }
        }
    }*/
}
