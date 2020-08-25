package com.sise.pet.controller.v2;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sise.pet.core.CommonResult;
import com.sise.pet.entity.Discussion;
import com.sise.pet.service.IDiscussionService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 讨论 前端控制器
 * </p>
 *
 * @author author
 * @since 2020-02-27
 */
@RestController
@RequestMapping(value = {"/api/v1/discussion","/api/v2/discussion"})
public class DiscussionController {

    @Resource
    private IDiscussionService iDiscussionService;

    @PostMapping
    public CommonResult add(Discussion entity){
        entity.setCreateTime(new Date());
        iDiscussionService.save(entity);
        return CommonResult.success(null);
    }

    @DeleteMapping("/{id}")
    public CommonResult delete(@PathVariable Integer id){
        iDiscussionService.deleteById(id);
        return CommonResult.success(null);
    }

    @PutMapping
    public CommonResult update(Discussion entity){
        iDiscussionService.updateById(entity);
        return CommonResult.success(null);
    }

    @GetMapping
    public CommonResult list(Discussion entity, Page page){
        IPage<Discussion> list = iDiscussionService.selectPage(entity, page);
        return CommonResult.success(list);
    }

    @GetMapping("/{id}")
    public CommonResult get(@PathVariable Integer id){
        Discussion discussion = iDiscussionService.getSingleDiscussion(id);
        return CommonResult.success(discussion);
    }

    @GetMapping("/popular")
    public CommonResult getPopularDiscussion(){
        List<Discussion> discussions = iDiscussionService.getPopularDiscussion();
        return CommonResult.success(discussions);
    }

    @PutMapping("/updateViewCount/{id}")
    public CommonResult updateViewCount(@PathVariable Integer id){
        iDiscussionService.updateViewCount(id);
        return CommonResult.success(null);
    }

}
