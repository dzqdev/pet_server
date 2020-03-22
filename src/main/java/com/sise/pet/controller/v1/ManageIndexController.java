package com.sise.pet.controller.v1;

import com.sise.pet.core.Result;
import com.sise.pet.core.ResultGenerator;
import com.sise.pet.service.IArticleService;
import com.sise.pet.service.IBoardingHomeService;
import com.sise.pet.service.IHospitalService;
import com.sise.pet.service.IPetService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName ManageIndexController
 * @Description TODO
 * @Date 2020/3/21 23:14
 * @Version 1.0
 **/
@RestController
@RequestMapping("/api/v1/index")
public class ManageIndexController {

    @Resource
    private IPetService petService;

    @Resource
    private IHospitalService hospitalService;

    @Resource
    private IArticleService articleService;

    @Resource
    private IBoardingHomeService boardingHomeService;

    @GetMapping("/count")
    public Result getCount(){
        Map map = new HashMap();
        int petCount = petService.count();
        int hospitalCount = hospitalService.count();
        int articleCount = articleService.count();
        int boardingHomeCount = boardingHomeService.count();
        map.put("petCount",petCount);
        map.put("hospitalCount",hospitalCount);
        map.put("articleCount",articleCount);
        map.put("boardingHomeCount",boardingHomeCount);
        return ResultGenerator.genSuccessResult(map);
    }
}
