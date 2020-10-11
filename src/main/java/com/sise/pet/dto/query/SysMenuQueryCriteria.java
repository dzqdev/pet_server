package com.sise.pet.dto.query;

import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

/**
 * @ClassName SysMenuQueryCriteria
 * @Description TODO
 * @Date 2020/10/11 20:26
 * @Version 1.0
 **/
@Data
public class SysMenuQueryCriteria {
    /**
     * 父节点id
     */
    private Long pid;

    /**
     * 模糊查询关键字
     */
    private String blurry;

    /**
     * 创建时间
     */
    private List<Timestamp> createTime;
}
