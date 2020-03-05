package com.sise.pet.service.impl;

import com.sise.pet.entity.Manager;
import com.sise.pet.mapper.ManagerMapper;
import com.sise.pet.service.IManagerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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

}
