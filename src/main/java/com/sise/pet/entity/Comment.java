package com.sise.pet.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 评论
 * </p>
 *
 * @author author
 * @since 2020-02-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Comment implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 回复内容
     */
    private String content;

    /**
     * 讨论id
     */
    private Integer discussionId;

    /**
     * 发表人id
     */
    private Integer authorId;

    /**
     * 上一级评论
     */
    private Integer parentId;

    /**
     * 评论目标
     */
    private Integer toUid;

    /**
     * 0 文章的评论 1 评论的评论 2 评论的回复 @
     */
    private String level;

    /**
     * 创建时间
     */
    private Date createDate;


}