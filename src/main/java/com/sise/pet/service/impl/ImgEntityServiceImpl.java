package com.sise.pet.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sise.pet.entity.ImgEntity;
import com.sise.pet.mapper.ImgEntityMapper;
import com.sise.pet.service.IImgEntityService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 * 图片-实体中间表 服务实现类
 * </p>
 *
 * @author author
 * @since 2020-02-23
 */
@Service
public class ImgEntityServiceImpl extends ServiceImpl<ImgEntityMapper, ImgEntity> implements IImgEntityService {

    @Resource
    private ImgEntityMapper imgEntityMapper;

    @Override
    public Page<ImgEntity> selectPage(ImgEntity entity, Page page) {
        QueryWrapper<ImgEntity> queryWrapper = new QueryWrapper<>();
        Page result = imgEntityMapper.selectPage(page, queryWrapper);
        return result;
    }
}
