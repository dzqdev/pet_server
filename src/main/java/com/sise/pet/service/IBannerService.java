package com.sise.pet.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sise.pet.entity.Banner;

/**
 * <p>
 * 轮播图 服务类
 * </p>
 *
 * @author author
 * @since 2020-02-23
 */
public interface IBannerService extends IService<Banner> {
    Page<Banner> selectPage(Banner banner, Page page);
}
