package com.sise.pet.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sise.pet.entity.Notice;

import java.util.List;

/**
 * <p>
 * 通知 服务类
 * </p>
 *
 * @author author
 * @since 2020-03-12
 */
public interface INoticeService extends IService<Notice> {
    List<Notice> getUserNoticesByType(Integer userId,String type);
}
