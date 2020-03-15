package com.sise.pet.service.impl;

import com.sise.pet.entity.Comment;
import com.sise.pet.mapper.CommentMapper;
import com.sise.pet.service.ICommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sise.pet.vo.CommentVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 评论 服务实现类
 * </p>
 *
 * @author author
 * @since 2020-02-27
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements ICommentService {

    @Resource
    private CommentMapper commentMapper;

    @Override
    public List<CommentVo> listCommentsByDiscussion(Integer id) {
        List<CommentVo> list = commentMapper.listCommentsByDiscussion(id, "0");
        if(null != list && list.size() > 0){
            for (CommentVo commentVo : list) {
                //查询该评论下的其他子评论
                List<CommentVo> subComments = commentMapper.listCommentSubComments(commentVo.getId());
                commentVo.setChildren(subComments);
            }
        }

        return list;
    }
}
