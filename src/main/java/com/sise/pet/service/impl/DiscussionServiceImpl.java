package com.sise.pet.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sise.pet.entity.Discussion;
import com.sise.pet.mapper.DiscussionMapper;
import com.sise.pet.service.IDiscussionService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 * 讨论 服务实现类
 * </p>
 *
 * @author author
 * @since 2020-02-27
 */
@Service
public class DiscussionServiceImpl extends ServiceImpl<DiscussionMapper, Discussion> implements IDiscussionService {

    @Resource
    private DiscussionMapper discussionMapper;

    @Override
    public IPage<Discussion> selectPage(Discussion entity, Page page) {
        IPage<Discussion> result = discussionMapper.getDetailInfoPage(page, entity);
        return result;
    }

    @Override
    public Discussion getSingleDiscussion(Integer id) {
        return discussionMapper.getSingleDiscussion(id);
    }
}
