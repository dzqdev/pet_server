package com.sise.pet.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sise.pet.dto.ArticleDto;
import com.sise.pet.entity.Article;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 文章表 Mapper 接口
 * </p>
 *
 * @author author
 * @since 2020-02-23
 */
public interface ArticleMapper extends BaseMapper<Article> {
    ArticleDto getArticleWithPetInfo(Integer id);
    IPage<ArticleDto> getArticlePage(Page page,@Param("article") ArticleDto article);
    void updateViewCount(Integer id);
}
