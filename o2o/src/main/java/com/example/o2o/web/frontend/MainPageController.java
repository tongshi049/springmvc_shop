package com.example.o2o.web.frontend;

import com.example.o2o.entity.HeadLine;
import com.example.o2o.entity.ShopCategory;
import com.example.o2o.service.HeadLineService;
import com.example.o2o.service.ShopCategoryService;
import com.example.o2o.util.PathUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("frontend")
public class MainPageController {
    @Autowired
    private ShopCategoryService shopCategoryService;
    @Autowired
    private HeadLineService headLineService;


    @RequestMapping(value = "/listmainpageinfo",method = RequestMethod.GET)
    @ResponseBody
    private Map<String,Object> listMainPageInfo(){
        Map<String,Object> modelMap = new HashMap<>();
        List<ShopCategory> shopCategoryList = new ArrayList<>();
        try{
            shopCategoryList = shopCategoryService.getShopCategoryList(null);
            modelMap.put("shopCategoryList",shopCategoryList);
        }catch (Exception e){
            modelMap.put("success",false);
            modelMap.put("errMsg",e.getMessage());
            return modelMap;
        }
        List<HeadLine> headLineList = new ArrayList<>();
        try{
            HeadLine headLineCondition = new HeadLine();
            headLineCondition.setEnableStatus(1); // select only valid headline

            headLineList = headLineService.getHeadLineList(headLineCondition);
            // iterate all headline and set their absolute path
            /*for(HeadLine headLine: headLineList){
                String relativeImgPath = headLine.getLineImg();
                headLine.setLineImg(PathUtil.getHeadLineImagePath(relativeImgPath));
            }*/
            modelMap.put("headLineList",headLineList);
        }catch (Exception e){
            modelMap.put("success",false);
            modelMap.put("errMsg",e.getMessage());
            return modelMap;
        }
        modelMap.put("success",true);
        return modelMap;
    }
}
