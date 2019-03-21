package com.example.o2o.web.frontend;

import com.example.o2o.entity.Product;
import com.example.o2o.service.ProductService;
import com.example.o2o.util.HttpServletRequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(value = "/frontend")
public class ProductDetailController {
    @Autowired
    private ProductService productService;

    @RequestMapping(value = "/listproductdetailpageinfo", method = RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> getProductDetailInfo(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        long productId = HttpServletRequestUtil.getLong(request, "productId");
        if (productId <= -1) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "productId required");
            return modelMap;
        }
        try {
            Product product = productService.getProductById(productId);
            modelMap.put("success", true);
            modelMap.put("product",product);
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.getMessage());
        } finally {
            return modelMap;
        }
    }
}
