package com.sise.pet.controller.v1;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sise.pet.core.Result;
import com.sise.pet.core.ResultGenerator;
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
@RequestMapping("/api/v1/boarding-home")
public class BoardingHomeController {

    @Resource
    private IBoardingHomeService iBoardingHomeService;

    @PostMapping
    public Result addBoardingHome(BoardingHome entity){
        entity.setCreateTime(new Date());
        iBoardingHomeService.save(entity);
        return ResultGenerator.genSuccessResult();
    }

    @PutMapping
    public Result updateBoardingHome(BoardingHome entity){
        iBoardingHomeService.updateById(entity);
        return ResultGenerator.genSuccessResult();
    }

    @DeleteMapping("/{id}")
    public Result deleteBoardingHome(@PathVariable String id){
        iBoardingHomeService.removeById(id);
        return ResultGenerator.genSuccessResult();
    }

    @GetMapping("/{id}")
    public Result get(@PathVariable Integer id){
        BoardingHome entity = iBoardingHomeService.getById(id);
        return ResultGenerator.genSuccessResult(entity);
    }

    @GetMapping
    public Result list(BoardingHome entity, Page page){
        IPage<BoardingHome> list = iBoardingHomeService.selectPage(entity, page);
        return ResultGenerator.genSuccessResult(list);
    }

}
