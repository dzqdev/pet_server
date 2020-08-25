package com.sise.pet.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 文章表
 * </p>
 *
 * @author author
 * @since 2020-02-23
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Article implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 标题
     */
    private String title;

    /**
     * 描述
     */
    private String description;

    /**
     * 内容
     */
    private String content;

    /**
     * 作者
     */
    private String author;

    /**
     * 所属宠物狗类别id
     */
    private Integer petId;

    /**
     * 类型
     */
    private String type;

    /**
     * 查看次数
     */
    private Integer viewCount;

    /**
     * 创建日期
     */
    private Date createTime;

}
