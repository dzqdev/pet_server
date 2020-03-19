package com.sise.pet.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sise.pet.entity.Comment;
import com.sise.pet.entity.Discussion;
import com.sise.pet.entity.Pet;
import com.sise.pet.mapper.CommentMapper;
import com.sise.pet.mapper.DiscussionMapper;
import com.sise.pet.service.IDiscussionService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

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

    @Resource
    private CommentMapper commentMapper;

    @Override
    public IPage<Discussion> selectPage(Discussion entity, Page page) {
        IPage<Discussion> result = discussionMapper.getDetailInfoPage(page, entity);
        return result;
    }

    @Override
    public Discussion getSingleDiscussion(Integer id) {
        return discussionMapper.getSingleDiscussion(id);
    }

    @Override
    public void deleteById(Integer id) {
        discussionMapper.deleteById(id);
        //删除讨论的相关评论
        QueryWrapper<Comment> deleteWrapper = new QueryWrapper();
        deleteWrapper.eq("discussion_id", id);
        commentMapper.delete(deleteWrapper);
    }

    @Override
    public List<Discussion> getPopularDiscussion() {
        QueryWrapper<Discussion> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByAsc("view_count");
        queryWrapper.last("limit 0 , 5");
        List<Discussion> list = discussionMapper.selectList(queryWrapper);
        return list;
    }
}
