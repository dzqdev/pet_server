package com.sise.pet.controller.v1;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sise.pet.core.CommonResult;
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
@RequestMapping(value = {"/api/v1/banner","/api/v2/banner"})
public class BannerController {

    @Resource
    private IBannerService iBannerService;

    @PostMapping
    public CommonResult addBanner(Banner entity){
        entity.setCreateTime(new Date());
        iBannerService.save(entity);
        return CommonResult.success(null);
    }

    @PutMapping
    public CommonResult updateBanner(Banner entity){
        iBannerService.updateById(entity);
        return CommonResult.success(null);
    }

    @GetMapping
    public CommonResult bannerList(Banner entity, Page page){
        Page<Banner> list = iBannerService.selectPage(entity,page);
        return CommonResult.success(list);
    }

    @DeleteMapping("/{id}")
    public CommonResult deleteBanner(@PathVariable String id){
        iBannerService.removeById(id);
        return CommonResult.success(null);
    }

    /**
     * 返回客户端需要展示的banner
     * @return
     */
    @GetMapping("/visible")
    public CommonResult getVisibleBanner(){
        List<Banner> list =  iBannerService.getVisibleBanner();
        return CommonResult.success(list);
    }
}
