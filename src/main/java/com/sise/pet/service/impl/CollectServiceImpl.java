package com.sise.pet.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sise.pet.entity.Collect;
import com.sise.pet.mapper.CollectMapper;
import com.sise.pet.service.ICollectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sise.pet.vo.CollectArticleVo;
import com.sise.pet.vo.CollectVideoVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 收藏 服务实现类
 * </p>
 *
 * @author author
 * @since 2020-03-08
 */
@Service
public class CollectServiceImpl extends ServiceImpl<CollectMapper, Collect> implements ICollectService {

    @Resource
    private CollectMapper collectMapper;

    @Override
    public boolean judgeResourceIsCollect(Collect collect) {
        QueryWrapper<Collect> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("resource_id", collect.getResourceId());
        queryWrapper.eq("type", collect.getType());
        queryWrapper.eq("user_id", collect.getUserId());
        List<Collect> collects = collectMapper.selectList(queryWrapper);
        if(null != collect && collects.size() > 0 ){
            return true;
        }
        return false;
    }

    @Override
    public IPage<CollectArticleVo> getUserCollectArticles(Page page, Collect collect) {
        return collectMapper.getUserCollectArticles(page,collect);
    }

    @Override
    public IPage<CollectVideoVo> getUserCollectVideos(Page page, Collect collect) {
        return collectMapper.getUserCollectVideos(page,collect);
    }


}
