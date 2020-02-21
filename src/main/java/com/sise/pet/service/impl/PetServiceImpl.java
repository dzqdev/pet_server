package com.sise.pet.service.impl;

import com.sise.pet.entity.Pet;
import com.sise.pet.mapper.PetMapper;
import com.sise.pet.service.IPetService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 宠物狗类别 服务实现类
 * </p>
 *
 * @author author
 * @since 2020-02-21
 */
@Service
public class PetServiceImpl extends ServiceImpl<PetMapper, Pet> implements IPetService {

}
