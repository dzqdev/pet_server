package com.sise.pet.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sise.pet.entity.Manager;
import com.sise.pet.mapper.ManagerMapper;
import com.sise.pet.service.IManagerService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 * 管理员 服务实现类
 * </p>
 *
 * @author author
 * @since 2020-03-05
 */
@Service
public class ManagerServiceImpl extends ServiceImpl<ManagerMapper, Manager> implements IManagerService {

    @Resource
    private ManagerMapper managerMapper;

    @Override
    public Page<Manager> selectPage(Manager manager, Page page) {
        QueryWrapper<Manager> queryWrapper = new QueryWrapper<>();
        queryWrapper.ne("account", "admin");
        Page result = managerMapper.selectPage(page, queryWrapper);
        return result;
    }
}
