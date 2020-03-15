package com.sise.pet.utils;

import java.io.File;

/**
 * @ClassName UploadUtil
 * @Description TODO
 * @Date 2020/2/23 20:12
 * @Version 1.0
 **/
public class UploadUtil {
    public static final String IMAGE_UPLOAD_PATH = "D:/upload/images";

    public static final String VIDEO_UPLOAD_PATH = "D:/upload/videos";

    public static File getImageResourceSaveDir(){
        String fileDirPath = new String(IMAGE_UPLOAD_PATH);
        File fileDir = new File(fileDirPath);
        if(!fileDir.exists()){
            // 递归生成文件夹
            fileDir.mkdirs();
        }
        return fileDir;
    }

    public static File getVideoResourceSaveDir(){
        String fileDirPath = new String(VIDEO_UPLOAD_PATH);
        File fileDir = new File(fileDirPath);
        if(!fileDir.exists()){
            // 递归生成文件夹
            fileDir.mkdirs();
        }
        return fileDir;
    }

}
