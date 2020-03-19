package com.sise.pet.controller.v2;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sise.pet.core.Result;
import com.sise.pet.core.ResultGenerator;
import com.sise.pet.entity.Collect;
import com.sise.pet.service.ICollectService;
import com.sise.pet.vo.CollectArticleVo;
import com.sise.pet.vo.CollectVideoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * <p>
 * 收藏 前端控制器
 * </p>
 *
 * @author author
 * @since 2020-03-08
 */
@RestController
@RequestMapping("/api/v2/collect")
public class CollectController {

    @Autowired
    private ICollectService iCollectService;

    /**
     * 收藏文章
     *
     * @param collect
     * @return
     */
    @PostMapping("/article")
    public Result addArticle(Collect collect) {
        collect.setCreateTime(new Date());
        collect.setType("article");
        iCollectService.save(collect);
        return ResultGenerator.genSuccessResult();
    }

    /**
     * 收藏视频
     *
     * @param collect
     * @return
     */
    @PostMapping("/video")
    public Result addVideo(Collect collect) {
        collect.setCreateTime(new Date());
        collect.setType("video");
        iCollectService.save(collect);
        return ResultGenerator.genSuccessResult();
    }

    /**
     * 判断文章是否被某用户收藏过
     */
    @GetMapping("/judgeArticleIsCollect")
    public Result judgeArticleIsCollect(Collect collect) {
        collect.setType("article");
        boolean b = iCollectService.judgeResourceIsCollect(collect);
        return ResultGenerator.genSuccessResult(b);
    }

    /**
     * 判断视频是否被收藏过
     *
     * @param collect
     * @return
     */
    @GetMapping("/judgeVideoIsCollect")
    public Result judgeVideoIsCollect(Collect collect) {
        collect.setType("video");
        boolean b = iCollectService.judgeResourceIsCollect(collect);
        return ResultGenerator.genSuccessResult(b);
    }


    /**
     * 获取某个用户收藏的文章
     *
     * @param userId
     * @return
     */
    @GetMapping("/collectArticles/{userId}")
    public Result getUserCollectArticles(@PathVariable Integer userId,Page page) {
        Collect collect = new Collect();
        collect.setUserId(userId);
        IPage<CollectArticleVo> result = iCollectService.getUserCollectArticles(page, collect);
        return ResultGenerator.genSuccessResult(result);
    }

    /**
     * 获取某个用户收藏的视频
     * TODO
     * @param userId
     * @return
     */
    @GetMapping("/collectVideos/{userId}")
    public Result getUserCollectVideos(@PathVariable Integer userId, Page page) {
        Collect collect = new Collect();
        collect.setUserId(userId);
        IPage<CollectVideoVo> result = iCollectService.getUserCollectVideos(page, collect);
        return ResultGenerator.genSuccessResult(result);
    }

    /**
     * 取消收藏某个文章
     * @param collect
     * @return
     */
    @DeleteMapping("/article")
    public Result cancelArticleCollect(Collect collect){
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("user_id", collect.getUserId());
        queryWrapper.eq("resource_id", collect.getResourceId());
        queryWrapper.eq("type", "article");
        iCollectService.remove(queryWrapper);
        return ResultGenerator.genSuccessResult();
    }

    /**
     * 取消收藏某个视频
     * @param collect
     * @return
     */
    @DeleteMapping("/video")
    public Result cancelVideoCollect(Collect collect){
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("user_id", collect.getUserId());
        queryWrapper.eq("resource_id", collect.getResourceId());
        queryWrapper.eq("type", "video");
        iCollectService.remove(queryWrapper);
        return ResultGenerator.genSuccessResult();
    }

}
