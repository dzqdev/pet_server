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
 * 资源删除日志
 * </p>
 *
 * @author author
 * @since 2020-03-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class DeleteLog implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 删除资源类型 0:讨论 1:评论
     */
    private Integer type;

    /**
     * 资源id
     */
    private Integer resourceId;

    /**
     * 删除原因类型
     */
    private Integer reasonType;

    /**
     * 创建时间
     */
    private Date createTime;


}
