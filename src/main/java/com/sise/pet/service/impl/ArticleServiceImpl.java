package com.sise.pet.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sise.pet.entity.Article;
import com.sise.pet.mapper.ArticleMapper;
import com.sise.pet.service.IArticleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 * 文章表 服务实现类
 * </p>
 *
 * @author author
 * @since 2020-02-23
 */
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements IArticleService {

    @Resource
    private ArticleMapper articleMapper;

    @Override
    public Page<Article> selectPage(Article article, Page page) {
        QueryWrapper<Article> queryWrapper = new QueryWrapper<>();
        Page result = articleMapper.selectPage(page, queryWrapper);
        return result;
    }
}
