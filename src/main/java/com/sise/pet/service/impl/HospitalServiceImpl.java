package com.sise.pet.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sise.pet.entity.Hospital;
import com.sise.pet.mapper.HospitalMapper;
import com.sise.pet.service.IHospitalService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 * 医院信息 服务实现类
 * </p>
 *
 * @author author
 * @since 2020-02-23
 */
@Service
public class HospitalServiceImpl extends ServiceImpl<HospitalMapper, Hospital> implements IHospitalService {

    @Resource
    private HospitalMapper hospitalMapper;

    @Override
    public IPage<Hospital> selectPage(Hospital entity, Page page) {
        QueryWrapper<Hospital> queryWrapper = new QueryWrapper<>();
        Page result = hospitalMapper.selectPage(page, queryWrapper);
        return result;
    }
}
