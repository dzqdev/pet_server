package com.sise.pet.utils;

import java.io.File;

/**
 * @ClassName UploadUtil
 * @Description TODO
 * @Date 2020/2/23 20:12
 * @Version 1.0
 **/
public class UploadUtil {
    public static final String FILE_UPLOAD_PATH = "D:/upload/images";

    public static File getResourcesSaveDir(){
        String fileDirPath = new String(FILE_UPLOAD_PATH);
        File fileDir = new File(fileDirPath);
        if(!fileDir.exists()){
            // 递归生成文件夹
            fileDir.mkdirs();
        }
        return fileDir;
    }
}
