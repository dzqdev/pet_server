package com.sise.pet.controller.v1;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sise.pet.core.Result;
import com.sise.pet.core.ResultGenerator;
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
    public Result addPet(Pet pet){
        pet.setCreateTime(new Date());
        iPetService.save(pet);
        return ResultGenerator.genSuccessResult();
    }

    @GetMapping
    public Result petList(Pet pet, Page page){
        IPage<Pet> list = iPetService.selectPage(pet, page);
        return ResultGenerator.genSuccessResult(list);
    }

    /**
     * 返回所有的记录，不需要分页
     * @return
     */
    @GetMapping("/unPage")
    public Result getAllPets(){
        List<Pet> list = iPetService.list();
        return ResultGenerator.genSuccessResult(list);
    }

    @GetMapping("/{id}")
    public Result get(@PathVariable Integer id){
        Pet pet = iPetService.getById(id);
        return ResultGenerator.genSuccessResult(pet);
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

    @PutMapping("/{id}")
    public Result updateViewCount(@PathVariable Integer id){
        iPetService.updateViewCount(id);
        return ResultGenerator.genSuccessResult();
    }

    /**
     * 热门宠物狗
     * @return
     */
    @GetMapping("/hot")
    public Result getPopularPet(){
        List<Pet> list = iPetService.getPopularPet();
        return ResultGenerator.genSuccessResult(list);
    }
}
