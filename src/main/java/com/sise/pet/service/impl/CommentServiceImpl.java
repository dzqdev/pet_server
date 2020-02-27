package com.sise.pet.service.impl;

import com.sise.pet.entity.Comment;
import com.sise.pet.mapper.CommentMapper;
import com.sise.pet.service.ICommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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

}
