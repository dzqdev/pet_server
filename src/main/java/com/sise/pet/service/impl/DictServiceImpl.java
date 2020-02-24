package com.sise.pet.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sise.pet.entity.Dict;
import com.sise.pet.mapper.DictMapper;
import com.sise.pet.service.IDictService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 * 数据字典 服务实现类
 * </p>
 *
 * @author author
 * @since 2020-02-21
 */
@Service
public class DictServiceImpl extends ServiceImpl<DictMapper, Dict> implements IDictService {

    @Resource
    private DictMapper dictMapper;

    @Override
    public Page<Dict> selectPage(Dict dict, Page page) {
        QueryWrapper<Dict> queryWrapper = new QueryWrapper<>();
        Page result = dictMapper.selectPage(page, queryWrapper);
        return result;
    }
}
