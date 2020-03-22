package com.sise.pet.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sise.pet.entity.User;
import com.sise.pet.mapper.UserMapper;
import com.sise.pet.service.IUserService;
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
    public Boolean updatePassword(String account, String password) {
        boolean flag = userMapper.updatePassword(account, password);
        return flag;
    }
}
