package com.sise.pet.dto.query;

import lombok.Data;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
public class SysUserQueryCriteria {

    private Long id;

    private String blurry;

    private Boolean enabled;

    private List<Timestamp> createTime;
}
