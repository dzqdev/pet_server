package com.sise.pet.controller;

import com.sise.pet.core.Result;
import com.sise.pet.core.ResultGenerator;
import com.sise.pet.utils.UploadUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * @ClassName UploadController
 * @Description TODO
 * @Date 2020/2/23 20:14
 * @Version 1.0
 **/
@RequestMapping("/api/v1")
@RestController
public class UploadController {

    @PostMapping(value = {"pictures"})
    public Result fileUpload(MultipartFile file){
        String filename = file.getOriginalFilename();
        File fileDir = UploadUtil.getImageResourceSaveDir();
        try {
            // 构建真实的文件路径
            File newFile = new File(fileDir.getAbsolutePath() + File.separator + filename);
            file.transferTo(newFile);
            String filePath = "http://localhost:8888/images/" + filename;
            return ResultGenerator.genSuccessResult(filePath);
        } catch (IOException e) {
            e.printStackTrace();
            return ResultGenerator.genFailResult(e.getMessage());
        }
    }

    @PostMapping(value = {"videos"})
    public Result videoUpload(MultipartFile file){
        String filename = file.getOriginalFilename();
        File fileDir = UploadUtil.getVideoResourceSaveDir();
        try {
            // 构建真实的文件路径
            File newFile = new File(fileDir.getAbsolutePath() + File.separator + filename);
            file.transferTo(newFile);
            String filePath = "http://localhost:8888/videos/" + filename;
            return ResultGenerator.genSuccessResult(filePath);
        } catch (IOException e) {
            e.printStackTrace();
            return ResultGenerator.genFailResult(e.getMessage());
        }
    }
}
