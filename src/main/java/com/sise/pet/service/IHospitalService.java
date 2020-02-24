package com.sise.pet.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sise.pet.entity.Hospital;

/**
 * <p>
 * 医院信息 服务类
 * </p>
 *
 * @author author
 * @since 2020-02-23
 */
public interface IHospitalService extends IService<Hospital> {
    Page<Hospital> selectPage(Hospital entity, Page page);
}
