package com.sise.pet.service.impl;

import com.sise.pet.entity.WebLog;
import com.sise.pet.mapper.WebLogMapper;
import com.sise.pet.service.IWebLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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

}
