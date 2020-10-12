package com.sise.pet.dto.query;

import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

@Data
public class SysRoleQueryCriteria {
    private String blurry;
    private List<Timestamp> createTime;
}
