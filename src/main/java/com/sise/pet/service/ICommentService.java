package com.sise.pet.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sise.pet.entity.Comment;
import com.sise.pet.dto.CommentDto;

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

    List<CommentDto> listCommentsByDiscussion(Integer id);

    void saveComment(Comment comment);

    void deleteComment(Integer id);

    IPage<CommentDto> selectPage(Comment entity, Page page);
}
