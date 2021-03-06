package com.sise.pet.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sise.pet.entity.Comment;
import com.sise.pet.dto.CommentDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 评论 Mapper 接口
 * </p>
 *
 * @author author
 * @since 2020-02-27
 */
public interface CommentMapper extends BaseMapper<Comment> {

    List<CommentDto> listCommentsByDiscussion(Integer discussionId, @Param("level") String level);

    List<CommentDto> listCommentSubComments(Integer parentId);

    IPage<CommentDto> getCommentPage(Page page, @Param("comment") Comment comment);
}
