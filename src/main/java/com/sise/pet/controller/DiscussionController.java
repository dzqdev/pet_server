package com.sise.pet.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sise.pet.core.Result;
import com.sise.pet.core.ResultGenerator;
import com.sise.pet.entity.Discussion;
import com.sise.pet.entity.Hospital;
import com.sise.pet.service.IDiscussionService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 * 讨论 前端控制器
 * </p>
 *
 * @author author
 * @since 2020-02-27
 */
@RestController
@RequestMapping("/api/v1/discussion")
public class DiscussionController {

    @Resource
    private IDiscussionService iDiscussionService;

    @PostMapping
    public Result add(Discussion entity){
        iDiscussionService.save(entity);
        return ResultGenerator.genSuccessResult();
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id){
        iDiscussionService.removeById(id);
        return ResultGenerator.genSuccessResult();
    }

    @PutMapping
    public Result update(Discussion entity){
        iDiscussionService.updateById(entity);
        return ResultGenerator.genSuccessResult();
    }

    @GetMapping
    public Result list(Discussion entity, Page page){
        IPage<Discussion> list = iDiscussionService.selectPage(entity, page);
        return ResultGenerator.genSuccessResult();
    }

    @GetMapping("/{id}")
    public Result get(@PathVariable Integer id){
        return ResultGenerator.genSuccessResult();
    }
}