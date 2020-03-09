package com.sise.pet.controller.v2;


import com.sise.pet.core.Result;
import com.sise.pet.core.ResultGenerator;
import com.sise.pet.entity.Comment;
import com.sise.pet.service.ICommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 前端相关的评论 新增/查询
 * @author author
 * @since 2020-02-27
 */
@RestController
@RequestMapping("/api/v2/comment")
public class CommentController {

    @Autowired
    private ICommentService iCommentService;

    @PostMapping
    public Result add(Comment comment){
        iCommentService.save(comment);
        return ResultGenerator.genSuccessResult();
    }

    /**
     * 根据讨论id,获取讨论下的评论
     * @param id
     * @return
     */
    @GetMapping("/discussion/{id}")
    public Result listCommentsByDiscussion(@PathVariable("id") Integer id){

        return ResultGenerator.genSuccessResult();
    }


}
