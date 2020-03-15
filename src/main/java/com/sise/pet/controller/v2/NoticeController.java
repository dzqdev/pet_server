package com.sise.pet.controller.v2;


import com.sise.pet.core.Result;
import com.sise.pet.core.ResultGenerator;
import com.sise.pet.entity.Notice;
import com.sise.pet.service.INoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 通知 前端控制器
 * </p>
 *
 * @author author
 * @since 2020-03-12
 */
@RestController@RequestMapping(value = {"/api/v1/notice","/api/v2/notice"})
public class NoticeController {

    @Autowired
    private INoticeService iNoticeService;

    /**
     * 查询某个用户已读的通知
     * @param userId
     * @return
     */
    @GetMapping("/read/{userId}")
    public Result getReadNoticeByUserId(@PathVariable Integer userId){
        List<Notice> list = iNoticeService.getUserNoticesByType(userId, "read");
        return ResultGenerator.genSuccessResult(list);
    }

    /**
     * 未读的消息
     * @param userId
     * @return
     */
    @GetMapping("/un-read/{userId}")
    public Result getUnReadNoticeByUserId(@PathVariable Integer userId){
        List<Notice> list = iNoticeService.getUserNoticesByType(userId, "un_read");
        return ResultGenerator.genSuccessResult(list);
    }

    //修改某个通知状态为已读
    @PutMapping("/{id}")
    public Result updateNoticeState(@PathVariable Integer id){
        Notice notice = new Notice();
        notice.setId(id);
        notice.setReadTime(new Date());
        iNoticeService.updateById(notice);
        return ResultGenerator.genSuccessResult();
    }



}
