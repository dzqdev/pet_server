package com.sise.pet.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sise.pet.core.Result;
import com.sise.pet.core.ResultGenerator;
import com.sise.pet.entity.Article;
import com.sise.pet.service.IArticleService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 * 文章表 前端控制器
 * </p>
 *
 * @author author
 * @since 2020-02-23
 */
@RestController
@RequestMapping("/api/v1/article")
public class ArticleController {

    @Resource
    private IArticleService iArticleService;

    @PostMapping
    public Result addArticle(Article article){
        iArticleService.save(article);
        return ResultGenerator.genSuccessResult();
    }

    @PutMapping
    public Result updateArticle(Article article){
        iArticleService.updateById(article);
        return ResultGenerator.genSuccessResult();
    }

    @GetMapping
    public Result articleList(Article article, Page page){
        IPage<Article> articleIPage = iArticleService.selectPage(article, page);
        return ResultGenerator.genSuccessResult(articleIPage);
    }

    @GetMapping("/{id}")
    public Result article(@PathVariable Integer id){
        Article article = iArticleService.getArticleWithPetInfo(id);
        return ResultGenerator.genSuccessResult(article);
    }

    @PutMapping("/{id}")
    public Result updateViewCount(@PathVariable Integer id){
        iArticleService.updateViewCount(id);
        return ResultGenerator.genSuccessResult();
    }
}
