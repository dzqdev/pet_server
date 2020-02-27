package com.sise.pet.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 讨论
 * </p>
 *
 * @author author
 * @since 2020-02-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Discussion implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 标题
     */
    private String title;

    /**
     * 内容
     */
    private String content;

    /**
     * 查看数量
     */
    private Integer viewCount;

    /**
     * 作者id
     */
    private Integer authorId;

    /**
     * 创建时间
     */
    private Date createTime;


    @TableField(exist = false)
    private User author;

    @TableField(exist = false)
    private List<Comment> comments;


}
