package com.sise.pet.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sise.pet.entity.User;
import com.sise.pet.mapper.UserMapper;
import com.sise.pet.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 * 用户 服务实现类
 * </p>
 *
 * @author author
 * @since 2020-02-27
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Resource
    private UserMapper userMapper;

    @Override
    public Boolean checkOldPassword(String account, String password) {
        QueryWrapper<User> queryWrapper = new QueryWrapper();
        queryWrapper.eq("account", account);
        queryWrapper.eq("password", password);
        Integer count = userMapper.selectCount(queryWrapper);
        return count > 0;
    }
}
