package com.sise.pet.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sise.pet.entity.Pet;

import java.util.List;

/**
 * <p>
 * 宠物狗类别 服务类
 * </p>
 *
 * @author author
 * @since 2020-02-21
 */
public interface IPetService extends IService<Pet> {


    IPage<Pet> selectPage(Pet entity, Page page);

    void updateViewCount(Integer id);

    List<Pet> getPopularPet();
}
