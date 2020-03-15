package com.sise.pet.vo;

import com.sise.pet.entity.Comment;
import com.sise.pet.entity.Discussion;
import com.sise.pet.entity.User;
import lombok.Data;

import java.util.List;

/**
 * @ClassName CommentVo
 * @Description TODO
 * @Date 2020/3/8 23:20
 * @Version 1.0
 **/
@Data
public class CommentVo extends Comment {
    private Discussion discussion;
    private User toUser;
    private User author;
    private Comment parent;
    private List<CommentVo> children;
}
