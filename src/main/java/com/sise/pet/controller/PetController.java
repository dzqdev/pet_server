package com.sise.pet.controller;


import com.sise.pet.core.Result;
import com.sise.pet.core.ResultGenerator;
import com.sise.pet.entity.Pet;
import com.sise.pet.service.IPetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

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

    public Result addPet(Pet pet){
        return ResultGenerator.genSuccessResult();
    }

}
