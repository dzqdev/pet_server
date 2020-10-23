package com.sise.pet.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sise.pet.dto.SysUserDto;
import com.sise.pet.dto.convert.PageConvert;
import com.sise.pet.dto.query.WebLogQueryCriteria;
import com.sise.pet.entity.SysUser;
import com.sise.pet.entity.WebLog;
import com.sise.pet.mapper.WebLogMapper;
import com.sise.pet.service.IWebLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 * 操作日志 服务实现类
 * </p>
 *
 * @author author
 * @since 2020-08-25
 */
@Service
public class WebLogServiceImpl extends ServiceImpl<WebLogMapper, WebLog> implements IWebLogService {

    @Resource
    private WebLogMapper webLogMapper;

    @Override
    public Page<WebLog> queryAll(WebLogQueryCriteria criteria, Page pageable) {
        LambdaQueryWrapper<WebLog> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(WebLog::getUsername, criteria.getUsername());
        Page<WebLog> page = webLogMapper.selectPage(pageable, queryWrapper);
        return page;
    }
}
