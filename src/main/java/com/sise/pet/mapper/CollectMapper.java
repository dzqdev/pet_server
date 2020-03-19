package com.sise.pet.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sise.pet.entity.Collect;
import com.sise.pet.vo.CollectArticleVo;
import com.sise.pet.vo.CollectVideoVo;

/**
 * <p>
 * 收藏 Mapper 接口
 * </p>
 *
 * @author author
 * @since 2020-03-08
 */
public interface CollectMapper extends BaseMapper<Collect> {
    IPage<CollectArticleVo> getUserCollectArticles(Page page, Collect collect);

    IPage<CollectVideoVo> getUserCollectVideos(Page page, Collect collect);
}
