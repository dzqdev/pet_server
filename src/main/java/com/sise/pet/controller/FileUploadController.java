package com.sise.pet.controller;

import cn.hutool.core.util.ObjectUtil;
import com.sise.pet.utils.FileProperties;
import com.sise.pet.core.CommonResult;
import com.sise.pet.utils.FileUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;

@RestController
@RequestMapping("/api/v1/upload")
public class FileUploadController {

    @Resource
    private FileProperties properties;

    @PostMapping
    public CommonResult upload(MultipartFile multipartFile){
        FileUtil.checkSize(properties.getMaxSize(), multipartFile.getSize());
        String suffix = FileUtil.getExtensionName(multipartFile.getOriginalFilename());
        String type = FileUtil.getFileType(suffix);
        File file = FileUtil.upload(multipartFile, properties.getPath().getPath() + type +  File.separator);
        if(ObjectUtil.isNull(file)){
            return CommonResult.failed("上传失败");
        }
        return CommonResult.success(file.getPath());
    }

}
