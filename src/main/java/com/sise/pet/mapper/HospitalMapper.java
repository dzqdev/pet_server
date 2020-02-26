package com.sise.pet.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sise.pet.entity.Hospital;
import org.mybatis.spring.annotation.MapperScan;

/**
 * <p>
 * 医院信息 Mapper 接口
 * </p>
 *
 * @author author
 * @since 2020-02-23
 */
@MapperScan
public interface HospitalMapper extends BaseMapper<Hospital> {
    //分页关联查询
    IPage<Hospital> getWithSubImgPage(Page page,Hospital hospital);

    Hospital getSingleWithSubImg(Integer id);
}
