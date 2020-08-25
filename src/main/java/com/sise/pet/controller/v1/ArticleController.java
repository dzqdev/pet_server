package com.sise.pet.controller.v1;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sise.pet.core.CommonResult;
import com.sise.pet.dto.ArticleDto;
import com.sise.pet.entity.Article;
import com.sise.pet.service.IArticleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
@Api(tags = "ArticleController",description = "文章管理")
@RestController
@RequestMapping(value = {"/api/v1/article","/api/v2/article"})
public class ArticleController {

    @Resource
    private IArticleService iArticleService;

    @ApiOperation("新增文章")
    @PostMapping
    public CommonResult addArticle(Article article){
        article.setCreateTime(new Date());
        iArticleService.save(article);
        return CommonResult.success(null);
    }

    @ApiOperation("更新文章")
    @PutMapping
    public CommonResult updateArticle(Article article){
        boolean update = iArticleService.updateById(article);
        return CommonResult.success(update);
    }

    @ApiOperation("文章分页列表")
    @GetMapping
    public CommonResult articleList(ArticleDto article, Page page){
        IPage<ArticleDto> articleIPage = iArticleService.selectPage(article, page);
        return CommonResult.success(articleIPage);
    }

    @ApiOperation("根据id获取某个文章")
    @GetMapping("/{id}")
    public CommonResult article(@PathVariable Integer id){
        ArticleDto article = iArticleService.getArticleWithPetInfo(id);
        return CommonResult.success(article);
    }

    @ApiOperation("根据id更新某个文章的观看次数")
    @PutMapping("/{id}")
    public CommonResult updateViewCount(@PathVariable Integer id){
        iArticleService.updateViewCount(id);
        return CommonResult.success(null);
    }

    @ApiOperation("受欢迎的文章列表top5")
    @GetMapping("/popular")
    public CommonResult getPopularArticle(){
        List<Article> list = iArticleService.getPopularArticle();
        return CommonResult.success(list);
    }

    @ApiOperation("根据id删除某个文章")
    @DeleteMapping("/{id}")
    public CommonResult delete(@PathVariable Integer id){
        iArticleService.removeById(id);
        return CommonResult.success(null);
    }

}
