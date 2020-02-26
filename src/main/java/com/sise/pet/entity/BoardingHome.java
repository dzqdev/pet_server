package com.sise.pet.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 寄养所信息
 * </p>
 *
 * @author author
 * @since 2020-02-23
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class BoardingHome implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 名称
     */
    private String name;

    /**
     * 主图
     */
    private String mainPic;

    /**
     * 基地承诺
     */
    private String promise;

    /**
     * 服务特色
     */
    private String feature;

    /**
     * 客服点评
     */
    private String customComment;

    /**
     * 联系方式
     */
    private String concat;

    /**
     * 经度
     */
    private BigDecimal lng;

    /**
     * 纬度
     */
    private BigDecimal lat;

    /**
     * 位置信息
     */
    private String location;

    /**
     * 创建时间
     */
    private Date createTime;

    @TableField(exist = false)
    List<String> subImgs;


}
