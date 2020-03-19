package com.sise.pet.controller.v2;


import com.sise.pet.core.Result;
import com.sise.pet.core.ResultGenerator;
import com.sise.pet.entity.Comment;
import com.sise.pet.service.ICommentService;
import com.sise.pet.vo.CommentVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 前端相关的评论 新增/查询
 * @author author
 * @since 2020-02-27
 */
@RestController
@RequestMapping("/api/v2/comment")
public class CommentController {

    @Resource
    private ICommentService iCommentService;

    @PostMapping
    public Result add(Comment comment){
        iCommentService.saveComment(comment);
        return ResultGenerator.genSuccessResult();
    }

    /**
     * 根据讨论id,获取讨论下的评论
     * @param id
     * @return
     */
    @GetMapping("/discussion/{id}")
    public Result listCommentsByDiscussion(@PathVariable("id") Integer id){
        List<CommentVo> commentList = iCommentService.listCommentsByDiscussion(id);
        return ResultGenerator.genSuccessResult(commentList);
    }

    /**
     * 删除评论
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id){
        iCommentService.removeById(id);
        return ResultGenerator.genSuccessResult();
    }


}
