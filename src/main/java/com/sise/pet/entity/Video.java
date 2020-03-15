package com.sise.pet.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 视频
 * </p>
 *
 * @author author
 * @since 2020-03-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Video implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 标题
     */
    private String title;

    /**
     * 缩略图
     */
    private String thumbnail;

    /**
     * 视频地址
     */
    private String url;

    /**
     * 所属狗狗
     */
    private Integer petId;

    /**
     * 视频简介
     */
    private String description;

    /**
     * 点击量
     */
    private Integer viewCount;

    /**
     * 创建时间
     */
    private Date createTime;

    @TableField(exist = false)
    private Pet pet;


}
