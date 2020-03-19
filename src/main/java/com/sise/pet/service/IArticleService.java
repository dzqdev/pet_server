package com.sise.pet.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sise.pet.entity.Article;

import java.util.List;

/**
 * <p>
 * 文章表 服务类
 * </p>
 *
 * @author author
 * @since 2020-02-23
 */
public interface IArticleService extends IService<Article> {

    IPage<Article> selectPage(Article entity, Page page);

    Article getArticleWithPetInfo(Integer id);

    void updateViewCount(Integer id);

    List<Article> getPopularArticle();

}
