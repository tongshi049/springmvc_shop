package com.example.o2o.util;

import com.example.o2o.dto.ImageHolder;
import jdk.internal.util.xml.impl.Input;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import static sun.plugin2.os.windows.OSVERSIONINFOA.size;

public class ImageUtil {

    private static String basePath = "C:\\Users\\tshi3\\Desktop\\o2o\\src\\main\\resources";
    private static final SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
    private static final Random r = new Random();
    private static Logger logger = LoggerFactory.getLogger(ImageUtil.class);

    /**
     * cFile --> File
     *
     * @param cFile
     * @return
     */
    public static File transferCommonsMultipartFileToFile(CommonsMultipartFile cFile) {
        File newFile = new File(cFile.getOriginalFilename());
        try {
            cFile.transferTo(newFile);
        } catch (IOException e) {
            logger.error(e.toString());
            e.printStackTrace();
        }
        return newFile;
    }

    /**
     * deal with thumbnail, return new relative directory of new Img
     *
     * @param
     * @param targetAddr
     * @return
     */
    public static String generateThumbnail(ImageHolder thumbnail, String targetAddr) {
        String realFileName = getRandomFileName();
        String extension = getFileExtension(thumbnail.getImageName());
        makeDirPath(targetAddr);
        String relativeAddr = targetAddr + realFileName + extension;
        logger.debug("current relativeAddr is: " + relativeAddr);
        File dest = new File(PathUtil.getImgBasePath() + relativeAddr);
        logger.debug("current completeAddr is: " + PathUtil.getImgBasePath() + relativeAddr);
        try {
            Thumbnails.of(thumbnail.getImage()).size(200, 200)
                    .watermark(Positions.BOTTOM_RIGHT, ImageIO.read(new File(basePath + "\\watermark.jpg")), 0.25f)
                    .toFile(PathUtil.getImgBasePath() + relativeAddr);
        } catch (IOException e) {
            logger.error(e.toString());
            e.printStackTrace();
        }
        return relativeAddr;
    }

    /**
     * created files that involving in the target dir
     *
     * @param targetAddr
     */
    private static void makeDirPath(String targetAddr) {
        String realFileParentPath = PathUtil.getImgBasePath() + targetAddr;
        File dirPath = new File(realFileParentPath);
        if (!dirPath.exists()) {
            dirPath.mkdirs();
        }
    }

    /**
     * get extension name of input file
     *
     * @param
     * @return
     */
    private static String getFileExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf("."));
    }

    /**
     * Generate random file name, current time yyyy-MM-DD HH:mm:ss + five random digits
     *
     * @return
     */
    public static String getRandomFileName() {
        // get random 5 digits
        int rannum = r.nextInt(89999) + 10000;
        String nowTimeStr = sDateFormat.format(new Date());
        return nowTimeStr + rannum;
    }

    public static void main(String[] args) throws IOException {
        //String basePath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
        //System.out.println(basePath);
        Thumbnails.of(new File("C:\\codes\\img\\namei.jpg"))
                .size(200, 200).watermark(Positions.BOTTOM_RIGHT,
                ImageIO.read(new File("C:\\Users\\tshi3\\Desktop\\o2o\\src\\main\\resources\\watermark.jpg")), 0.25f).outputQuality(0.8f)
                .toFile("C:\\codes\\img\\nameinew.jpg");

    }

    /**
     * 1. idenfity the path is a file path or a directory path,
     * if it's a file path delete file;
     * else delete the file under that directory.
     *
     * @param storePath
     */
    public static void deleteFileOrPath(String storePath) {
        File fileOrPath = new File(PathUtil.getImgBasePath() + storePath);
        if (fileOrPath.exists()) {
            if (fileOrPath.isDirectory()) {
                File[] files = fileOrPath.listFiles();
                for (int i = 0; i < files.length; i++) {
                    files[i].delete();
                }
            }
            fileOrPath.delete();
        }
    }
}
