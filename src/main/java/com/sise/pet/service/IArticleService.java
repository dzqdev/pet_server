package com.sise.pet.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sise.pet.entity.Article;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 文章表 服务类
 * </p>
 *
 * @author author
 * @since 2020-02-23
 */
public interface IArticleService extends IService<Article> {

    Page<Article> selectPage(Article article, Page page);
}
