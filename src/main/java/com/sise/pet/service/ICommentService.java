package com.sise.pet.service;

import com.sise.pet.entity.Comment;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sise.pet.vo.CommentVo;

import java.util.List;

/**
 * <p>
 * 评论 服务类
 * </p>
 *
 * @author author
 * @since 2020-02-27
 */
public interface ICommentService extends IService<Comment> {

    List<CommentVo> listCommentsByDiscussion(Integer id);

    void saveComment(Comment comment);

    void deleteComment(Integer id);
}
