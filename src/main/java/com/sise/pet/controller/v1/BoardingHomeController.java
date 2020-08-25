package com.sise.pet.controller.v1;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sise.pet.core.CommonResult;
import com.sise.pet.entity.BoardingHome;
import com.sise.pet.service.IBoardingHomeService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;

/**
 * <p>
 * 寄养所信息 前端控制器
 * </p>
 *
 * @author author
 * @since 2020-02-23
 */
@RestController
@RequestMapping(value = {"/api/v1/boarding-home","/api/v2/boarding-home"})
public class BoardingHomeController {

    @Resource
    private IBoardingHomeService iBoardingHomeService;

    @PostMapping
    public CommonResult addBoardingHome(BoardingHome entity){
        entity.setCreateTime(new Date());
        iBoardingHomeService.save(entity);
        return CommonResult.success(null);
    }

    @PutMapping
    public CommonResult updateBoardingHome(BoardingHome entity){
        iBoardingHomeService.updateById(entity);
        return CommonResult.success(null);
    }

    @DeleteMapping("/{id}")
    public CommonResult deleteBoardingHome(@PathVariable String id){
        iBoardingHomeService.removeById(id);
        return CommonResult.success(null);
    }

    @GetMapping("/{id}")
    public CommonResult get(@PathVariable Integer id){
        BoardingHome entity = iBoardingHomeService.getById(id);
        return CommonResult.success(entity);
    }

    @GetMapping
    public CommonResult list(BoardingHome entity, Page page){
        IPage<BoardingHome> list = iBoardingHomeService.selectPage(entity, page);
        return CommonResult.success(list);
    }

}
