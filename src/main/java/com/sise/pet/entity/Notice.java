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
 * 通知
 * </p>
 *
 * @author author
 * @since 2020-03-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Notice implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 通知标题
     */
    private String title;

    /**
     * 通知内容
     */
    private String content;

    /**
     * 通知的用户id
     */
    private Integer userId;

    /**
     * 通知类型：1-系统通知 2-被回复通知
     */
    private Integer type;

    /**
     * 阅读时间,null表示还未阅读
     */
    private Date readTime;

    /**
     * 创建时间
     */
    private Date createTime;


}
