package com.sise.pet.dto;

import lombok.Data;

import java.util.List;

/**
 * @ClassName DictDto
 * @Description TODO
 * @Date 2020/10/18 19:05
 * @Version 1.0
 **/
@Data
public class DictDto {
    private Long id;

    private List<DictDetailDto> dictDetails;

    private String name;

    private String remark;
}
