package com.sise.pet.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sise.pet.entity.Banner;
import com.sise.pet.mapper.BannerMapper;
import com.sise.pet.service.IBannerService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 轮播图 服务实现类
 * </p>
 *
 * @author author
 * @since 2020-02-23
 */
@Service
public class BannerServiceImpl extends ServiceImpl<BannerMapper, Banner> implements IBannerService {

    @Resource
    private BannerMapper bannerMapper;

    @Override
    public Page<Banner> selectPage(Banner banner, Page page) {
        QueryWrapper<Banner> queryWrapper = new QueryWrapper<>();
        if(StringUtils.isNotBlank(banner.getAlt())){
            queryWrapper.like("alt", banner.getAlt());
        }
        Page result = bannerMapper.selectPage(page, queryWrapper);
        return result;
    }

    @Override
    public List<Banner> getVisibleBanner() {
        QueryWrapper<Banner> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("state", 0);
        return bannerMapper.selectList(queryWrapper);
    }
}
