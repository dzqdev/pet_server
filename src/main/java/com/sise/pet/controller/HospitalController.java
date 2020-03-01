package com.sise.pet.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sise.pet.core.Result;
import com.sise.pet.core.ResultGenerator;
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
@RequestMapping("/api/v1/hospital")
public class HospitalController {

    @Resource
    private IHospitalService iHospitalService;

    @PostMapping
    public Result add(Hospital entity){
        entity.setCreateTime(new Date());
        iHospitalService.save(entity);
        return ResultGenerator.genSuccessResult();
    }


    @PutMapping
    public Result update(Hospital entity){
        iHospitalService.updateById(entity);
        return ResultGenerator.genSuccessResult();
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable String id){
        iHospitalService.removeById(id);
        return ResultGenerator.genSuccessResult();
    }

    @GetMapping
    public Result list(Hospital entity, Page page){
        IPage<Hospital> list = iHospitalService.selectPage(entity, page);
        return ResultGenerator.genSuccessResult(list);
    }

    @GetMapping("/{id}")
    public Result get(@PathVariable Integer id){
        Hospital hospital = iHospitalService.getById(id);
        return ResultGenerator.genSuccessResult(hospital);
    }
}
