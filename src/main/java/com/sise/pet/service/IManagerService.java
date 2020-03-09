package com.sise.pet.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sise.pet.entity.Manager;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 管理员 服务类
 * </p>
 *
 * @author author
 * @since 2020-03-05
 */
public interface IManagerService extends IService<Manager> {

    Page<Manager> selectPage(Manager manager, Page page);
}
