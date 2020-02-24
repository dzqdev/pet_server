package com.sise.pet.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sise.pet.core.Result;
import com.sise.pet.core.ResultGenerator;
import com.sise.pet.entity.Pet;
import com.sise.pet.service.IPetService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;

/**
 * <p>
 * 宠物狗类别 前端控制器
 * </p>
 *
 * @author author
 * @since 2020-02-21
 */
@RestController
@RequestMapping("/api/v1/pet")
public class PetController {

    @Resource
    private IPetService iPetService;

    @PostMapping
    public Result addPet(Pet pet){
        pet.setCreateTime(new Date());
        iPetService.save(pet);
        return ResultGenerator.genSuccessResult();
    }

    @GetMapping
    public Result petList(Pet pet, Page page){
        Page<Pet> list = iPetService.selectPage(pet,page);
        return ResultGenerator.genSuccessResult(list);
    }

    @PutMapping
    public Result updatePet(Pet pet){
        iPetService.updateById(pet);
        return ResultGenerator.genSuccessResult();
    }


    @DeleteMapping("/{id}")
    public Result delPet(@PathVariable String id){
       iPetService.removeById(id);
        return ResultGenerator.genSuccessResult();
    }


}
