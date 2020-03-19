package com.sise.pet.service.impl;

import com.sise.pet.entity.DeleteLog;
import com.sise.pet.mapper.DeleteLogMapper;
import com.sise.pet.service.IDeleteLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 资源删除日志 服务实现类
 * </p>
 *
 * @author author
 * @since 2020-03-17
 */
@Service
public class DeleteLogServiceImpl extends ServiceImpl<DeleteLogMapper, DeleteLog> implements IDeleteLogService {

}
