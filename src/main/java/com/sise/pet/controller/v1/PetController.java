package com.sise.pet.controller.v1;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sise.pet.core.CommonResult;
import com.sise.pet.entity.Pet;
import com.sise.pet.service.IPetService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 宠物狗类别 前端控制器
 * </p>
 *
 * @author author
 * @since 2020-02-21
 */
@RestController
@RequestMapping(value = {"/api/v1/pet","/api/v2/pet"})
public class PetController {

    @Resource
    private IPetService iPetService;

    @PostMapping
    public CommonResult addPet(Pet pet){
        pet.setCreateTime(new Date());
        iPetService.save(pet);
        return CommonResult.success(null);
    }

    @GetMapping
    public CommonResult petList(Pet pet, Page page){
        IPage<Pet> list = iPetService.selectPage(pet, page);
        return CommonResult.success(list);
    }

    /**
     * 返回所有的记录，不需要分页
     * @return
     */
    @GetMapping("/unPage")
    public CommonResult getAllPets(){
        List<Pet> list = iPetService.list();
        return CommonResult.success(list);
    }

    @GetMapping("/{id}")
    public CommonResult get(@PathVariable Integer id){
        Pet pet = iPetService.getById(id);
        return CommonResult.success(pet);
    }

    @PutMapping
    public CommonResult updatePet(Pet pet){
        iPetService.updateById(pet);
        return CommonResult.success(null);
    }


    @DeleteMapping("/{id}")
    public CommonResult delPet(@PathVariable String id){
       iPetService.removeById(id);
        return CommonResult.success(null);
    }

    @PutMapping("/{id}")
    public CommonResult updateViewCount(@PathVariable Integer id){
        iPetService.updateViewCount(id);
        return CommonResult.success(null);
    }

    /**
     * 热门宠物狗
     * @return
     */
    @GetMapping("/popular")
    public CommonResult getPopularPet(){
        List<Pet> list = iPetService.getPopularPet();
        return CommonResult.success(list);
    }
}
