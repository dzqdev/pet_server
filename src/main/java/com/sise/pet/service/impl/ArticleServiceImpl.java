package com.sise.pet.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sise.pet.entity.Article;
import com.sise.pet.mapper.ArticleMapper;
import com.sise.pet.service.IArticleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

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
    public IPage<Article> selectPage(Article entity, Page page) {
        return articleMapper.getArticlePage(page,entity);
    }

    @Override
    public Article getArticleWithPetInfo(Integer id) {
        return articleMapper.getArticleWithPetInfo(id);
    }

    @Override
    public void updateViewCount(Integer id) {
        articleMapper.updateViewCount(id);
    }

    @Override
    public List<Article> getPopularArticle() {
        QueryWrapper<Article> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByAsc("view_count");
        queryWrapper.last("limit 0 , 5");
        List<Article> list = articleMapper.selectList(queryWrapper);
        return list;
    }
}
