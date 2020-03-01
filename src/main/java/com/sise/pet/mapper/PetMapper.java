package com.sise.pet.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sise.pet.entity.Pet;

/**
 * <p>
 * 宠物狗类别 Mapper 接口
 * </p>
 *
 * @author author
 * @since 2020-02-21
 */
public interface PetMapper extends BaseMapper<Pet> {
    void updateViewCount(Integer id);
}
