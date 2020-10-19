package com.sise.pet.dto;

import lombok.Data;

/**
 * @ClassName DictDetailDto
 * @Description TODO
 * @Date 2020/10/18 19:07
 * @Version 1.0
 **/
@Data
public class DictDetailDto {
    private Long id;
    private String label;
    private String value;
    private String sort;
    private String dictName;
}
