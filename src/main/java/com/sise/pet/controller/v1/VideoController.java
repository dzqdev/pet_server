package com.sise.pet.controller.v1;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sise.pet.core.Result;
import com.sise.pet.core.ResultGenerator;
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
    public Result add(Video video){
        iVideoService.save(video);
        return ResultGenerator.genSuccessResult();
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id){
        iVideoService.removeById(id);
        return ResultGenerator.genSuccessResult();
    }

    @PutMapping
    public Result update(Video video){
        iVideoService.updateById(video);
        return ResultGenerator.genSuccessResult();
    }

    @GetMapping
    public Result list(Page page,Video video){
        IPage<Video> list = iVideoService.selectWithPetInfoPage(page, video);
        return ResultGenerator.genSuccessResult(list);
    }

    @GetMapping("/{id}")
    public Result get(@PathVariable Integer id){
        Video video = iVideoService.selectWithPetInfo(id);
        return ResultGenerator.genSuccessResult(video);
    }

    @PutMapping("/{id}")
    public Result updateViewCount(@PathVariable Integer id){
        iVideoService.updateViewCount(id);
        return ResultGenerator.genSuccessResult();
    }
}
