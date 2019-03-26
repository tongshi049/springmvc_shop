package com.example.o2o.interceptor.shopadmin;

import com.example.o2o.entity.PersonInfo;
import com.example.o2o.entity.Shop;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.List;

public class ShopPermissionInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // get current shop from session
        Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
        @SuppressWarnings("unchecked")
        //get shoplist from session
        List<Shop> shopList = (List<Shop>) request.getSession().getAttribute("shopList");
        // not null identification
        if (currentShop != null && shopList != null) {
            // traverse shop list
            for (Shop shop : shopList) {
                // if current shop is in the shoplist, then user can continue the operation
                if (shop.getShopId() == currentShop.getShopId())
                    return true;
            }
        }
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        super.afterCompletion(request, response, handler, ex);
    }
}
