package com.sise.pet.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sise.pet.dto.SysUserDto;
import com.sise.pet.dto.query.SysUserQueryCriteria;
import com.sise.pet.dto.query.WebLogQueryCriteria;
import com.sise.pet.entity.WebLog;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 操作日志 服务类
 * </p>
 *
 * @author author
 * @since 2020-08-25
 */
public interface IWebLogService extends IService<WebLog> {
    Page<WebLog> queryAll(WebLogQueryCriteria criteria, Page pageable);
}
