package com.sise.pet.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sise.pet.core.Result;
import com.sise.pet.core.ResultGenerator;
import com.sise.pet.entity.Banner;
import com.sise.pet.service.IBannerService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 轮播图 前端控制器
 * </p>
 *
 * @author author
 * @since 2020-02-23
 */
@RestController
@RequestMapping("/api/v1/banner")
public class BannerController {

    @Resource
    private IBannerService iBannerService;

    @PostMapping
    public Result addBanner(Banner entity){
        entity.setCreateTime(new Date());
        iBannerService.save(entity);
        return ResultGenerator.genSuccessResult();
    }

    @PutMapping
    public Result updateBanner(Banner entity){
        iBannerService.updateById(entity);
        return ResultGenerator.genSuccessResult();
    }

    @GetMapping
    public Result bannerList(Banner entity,Page page){
        Page<Banner> list = iBannerService.selectPage(entity,page);
        return ResultGenerator.genSuccessResult(list);
    }

    @DeleteMapping("/{id}")
    public Result deleteBanner(@PathVariable String id){
        iBannerService.removeById(id);
        return ResultGenerator.genSuccessResult();
    }

    /**
     * 返回客户端需要展示的banner
     * @return
     */
    @GetMapping("/visible")
    public Result getVisibleBanner(){
        List<Banner> list =  iBannerService.getVisibleBanner();
        return ResultGenerator.genSuccessResult(list);
    }
}
