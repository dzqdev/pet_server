package com.sise.pet.controller.v2;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sise.pet.core.CommonResult;
import com.sise.pet.entity.Comment;
import com.sise.pet.service.ICommentService;
import com.sise.pet.vo.CommentVo;
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
@RequestMapping(value = {"/api/v1/comment","/api/v2/comment"})
public class CommentController {

    @Resource
    private ICommentService iCommentService;

    @PostMapping
    public CommonResult add(Comment comment){
        comment.setCreateDate(new Date());
        iCommentService.saveComment(comment);
        return CommonResult.success(null);
    }

    /**
     * 根据讨论id,获取讨论下的评论,分层级
     * @param id
     * @return
     */
    @GetMapping("/discussion/{id}")
    public CommonResult listCommentsByDiscussion(@PathVariable("id") Integer id){
        List<CommentVo> commentList = iCommentService.listCommentsByDiscussion(id);
        return CommonResult.success(commentList);
    }

    @GetMapping("/list/{id}")
    public CommonResult listCommentsByDiscussionWithoutLevel(@PathVariable("id") Integer id, Page page){
        Comment comment = new Comment();
        comment.setDiscussionId(id);
        IPage<CommentVo> list = iCommentService.selectPage(comment, page);
        return CommonResult.success(list);
    }

    /**
     * 删除评论
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public CommonResult delete(@PathVariable Integer id){
        iCommentService.removeById(id);
        return CommonResult.success(null);
    }


}
