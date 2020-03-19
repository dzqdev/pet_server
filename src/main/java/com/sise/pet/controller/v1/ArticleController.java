package com.sise.pet.controller.v1;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sise.pet.core.Result;
import com.sise.pet.core.ResultGenerator;
import com.sise.pet.entity.Article;
import com.sise.pet.service.IArticleService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 文章表 前端控制器
 * </p>
 *
 * @author author
 * @since 2020-02-23
 */
@RestController
@RequestMapping(value = {"/api/v1/article","/api/v2/article"})
public class ArticleController {

    @Resource
    private IArticleService iArticleService;

    @PostMapping
    public Result addArticle(Article article){
        article.setCreateTime(new Date());
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

    @GetMapping("/popular")
    public Result getPopularArticle(){
        List<Article> list = iArticleService.getPopularArticle();
        return ResultGenerator.genSuccessResult(list);
    }

}
