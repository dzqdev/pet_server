package com.sise.pet.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 数据字典
 * </p>
 *
 * @author author
 * @since 2020-02-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Dict implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 字典名称
     */
    private String name;

    /**
     * 描述
     */
    private String remark;

    /**
     * 创建日期
     */
    private LocalDateTime createTime;

    @TableField(exist = false)
    private List<DictDetail> dictDetails;


}
