package com.sise.pet.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sise.pet.entity.Collect;
import com.sise.pet.dto.CollectArticleDto;
import com.sise.pet.dto.CollectVideoDto;

/**
 * <p>
 * 收藏 服务类
 * </p>
 *
 * @author author
 * @since 2020-03-08
 */
public interface ICollectService extends IService<Collect> {
    /**
     * 判断文章是否被收藏
     * @param collect
     * @return
     */
    boolean judgeResourceIsCollect(Collect collect);

    /**
     * 查询某个用户收藏的文章
     * @param page
     * @param collect
     * @return
     */

    IPage<CollectArticleDto> getUserCollectArticles(Page page, Collect collect);

    IPage<CollectVideoDto> getUserCollectVideos(Page page, Collect collect);
}
