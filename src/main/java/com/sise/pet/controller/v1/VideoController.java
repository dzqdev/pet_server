package com.sise.pet.controller.v1;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sise.pet.core.CommonResult;
import com.sise.pet.entity.Video;
import com.sise.pet.service.IVideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 视频 前端控制器
 * </p>
 *
 * @author author
 * @since 2020-03-14
 */
@RestController
@RequestMapping(value = {"/api/v1/video","/api/v2/video"})
public class VideoController {

    @Autowired
    private IVideoService iVideoService;

    @PostMapping
    public CommonResult add(Video video){
        iVideoService.save(video);
        return CommonResult.success(null);
    }

    @DeleteMapping("/{id}")
    public CommonResult delete(@PathVariable Integer id){
        iVideoService.removeById(id);
        return CommonResult.success(null);
    }

    @PutMapping
    public CommonResult update(Video video){
        iVideoService.updateById(video);
        return CommonResult.success(null);
    }

    @GetMapping
    public CommonResult list(Page page, Video video){
        IPage<Video> list = iVideoService.selectWithPetInfoPage(page, video);
        return CommonResult.success(list);
    }

    @GetMapping("/{id}")
    public CommonResult get(@PathVariable Integer id){
        Video video = iVideoService.selectWithPetInfo(id);
        return CommonResult.success(video);
    }

    @PutMapping("/{id}")
    public CommonResult updateViewCount(@PathVariable Integer id){
        iVideoService.updateViewCount(id);
        return CommonResult.success(null);
    }
}
