package com.sise.pet.controller.v1;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sise.pet.core.CommonResult;
import com.sise.pet.entity.Hospital;
import com.sise.pet.service.IHospitalService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;

/**
 * <p>
 * 医院信息 前端控制器
 * </p>
 *
 * @author author
 * @since 2020-02-23
 */
@RestController
@RequestMapping(value = {"/api/v1/hospital","/api/v2/hospital"})
public class HospitalController {

    @Resource
    private IHospitalService iHospitalService;

    @PostMapping
    public CommonResult add(Hospital entity){
        entity.setCreateTime(new Date());
        iHospitalService.save(entity);
        return CommonResult.success(null);
    }


    @PutMapping
    public CommonResult update(Hospital entity){
        iHospitalService.updateById(entity);
        return CommonResult.success(null);
    }

    @DeleteMapping("/{id}")
    public CommonResult delete(@PathVariable String id){
        iHospitalService.removeById(id);
        return CommonResult.success(null);
    }

    @GetMapping
    public CommonResult list(Hospital entity, Page page){
        IPage<Hospital> list = iHospitalService.selectPage(entity, page);
        return CommonResult.success(list);
    }

    @GetMapping("/{id}")
    public CommonResult get(@PathVariable Integer id){
        Hospital hospital = iHospitalService.getById(id);
        return CommonResult.success(hospital);
    }
}
