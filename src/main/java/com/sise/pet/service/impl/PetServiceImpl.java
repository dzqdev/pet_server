package com.sise.pet.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sise.pet.entity.Pet;
import com.sise.pet.mapper.PetMapper;
import com.sise.pet.service.IPetService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

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

    @Resource
    private PetMapper petMapper;


    @Override
    public IPage<Pet> selectPage(Pet entity, Page page) {
        QueryWrapper<Pet> queryWrapper = new QueryWrapper<>();
        Page result = petMapper.selectPage(page, queryWrapper);
        return result;
    }

    @Override
    public void updateViewCount(Integer id) {
        petMapper.updateViewCount(id);
    }

    @Override
    public List<Pet> getHotPet() {
        QueryWrapper<Pet> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByAsc("view_count");
        queryWrapper.last("limit 0 , 5");
        List<Pet> list = petMapper.selectList(queryWrapper);
        return list;
    }

}
