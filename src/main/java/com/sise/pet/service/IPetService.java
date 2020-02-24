package com.sise.pet.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sise.pet.entity.Pet;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 宠物狗类别 服务类
 * </p>
 *
 * @author author
 * @since 2020-02-21
 */
public interface IPetService extends IService<Pet> {

    Page<Pet> selectPage(Pet pet, Page page);
}
