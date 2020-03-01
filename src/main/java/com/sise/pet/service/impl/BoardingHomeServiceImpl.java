package com.sise.pet.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sise.pet.entity.BoardingHome;
import com.sise.pet.mapper.BoardingHomeMapper;
import com.sise.pet.service.IBoardingHomeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 * 寄养所信息 服务实现类
 * </p>
 *
 * @author author
 * @since 2020-02-23
 */
@Service
public class BoardingHomeServiceImpl extends ServiceImpl<BoardingHomeMapper, BoardingHome> implements IBoardingHomeService {

    @Resource
    private BoardingHomeMapper boardingHomeMapper;

    @Override
    public IPage<BoardingHome> selectPage(BoardingHome entity, Page page) {
        QueryWrapper<BoardingHome> queryWrapper = new QueryWrapper<>();
        Page result = boardingHomeMapper.selectPage(page, queryWrapper);
        return result;
    }
}
