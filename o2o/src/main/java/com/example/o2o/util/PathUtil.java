package com.example.o2o.util;

public class PathUtil {

    /*to make file path separation java code platform independent
    * */
    private static String separator = System.getProperty("file.separator");

    public static String getImgBasePath() {

        String os = System.getProperty("os.name");
        String basePath = "";
        if (os.toLowerCase().startsWith("win")) {
            basePath = "C:/codes/img/";
        } else {
            basePath = "/home/garen/img/";
        }
        basePath = basePath.replace("/", separator);//useless
        return basePath;
    }

    public static String getShopImagePath(long shopId) {
        String imagePath = "upload/item/shop/" + shopId + "/";

        return imagePath.replace("/", separator);
    }
}
