package com.sise.pet.mapper;

import com.sise.pet.entity.Comment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sise.pet.vo.CommentVo;
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

    List<CommentVo> listCommentsByDiscussion(Integer discussionId, @Param("level") String level);

    List<CommentVo> listCommentSubComments(Integer parentId);
}
