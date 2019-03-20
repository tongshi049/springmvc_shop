package com.example.o2o.util;

import com.example.o2o.entity.HeadLine;

public class PathUtil {

    /*to make file path separation java code platform independent
    * */
    private static String separator = System.getProperty("file.separator");

    public static String getImgBasePath() {

        String os = System.getProperty("os.name");
        String basePath = "";
        if (os.toLowerCase().startsWith("win")) {
            basePath = "C:/codes/img";//"C:/codes/img/";
        } else {
            basePath = "/home/garen/img";
        }
        basePath = basePath.replace("/", separator);//useless
        return basePath;
    }

    public static String getShopImagePath(long shopId) {
        String imagePath = "/upload/images/item/shop/" + shopId + "/";

        return imagePath.replace("/", separator);
    }

    public static String getHeadLineImagePath(String relativeImgPath){
        String basePath = PathUtil.getImgBasePath();
        String relativePath = relativeImgPath.substring(1).replace("/",separator);
        return basePath +relativePath;
    }

}
