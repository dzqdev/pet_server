package com.sise.pet.service.impl;

import com.alibaba.druid.sql.visitor.functions.If;
import com.sise.pet.entity.Comment;
import com.sise.pet.entity.Discussion;
import com.sise.pet.entity.Notice;
import com.sise.pet.entity.User;
import com.sise.pet.mapper.CommentMapper;
import com.sise.pet.mapper.DiscussionMapper;
import com.sise.pet.mapper.NoticeMapper;
import com.sise.pet.mapper.UserMapper;
import com.sise.pet.service.ICommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sise.pet.utils.Constant;
import com.sise.pet.vo.CommentVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 评论 服务实现类
 * </p>
 *
 * @author author
 * @since 2020-02-27
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements ICommentService {

    @Resource
    private CommentMapper commentMapper;

    @Resource
    private NoticeMapper noticeMapper;

    @Resource
    private DiscussionMapper discussionMapper;

    @Resource
    private UserMapper userMapper;

    @Override
    public List<CommentVo> listCommentsByDiscussion(Integer id) {
        List<CommentVo> list = commentMapper.listCommentsByDiscussion(id, "0");
        if(null != list && list.size() > 0){
            for (CommentVo commentVo : list) {
                //查询该评论下的其他子评论
                List<CommentVo> subComments = commentMapper.listCommentSubComments(commentVo.getId());
                commentVo.setChildren(subComments);
            }
        }

        return list;
    }

    @Override
    public void saveComment(Comment comment) {
        //保存评论
        commentMapper.insert(comment);
        //做通知
        Notice notice = new Notice();
        StringBuffer title = new StringBuffer();
        StringBuffer content = new StringBuffer();
        if(Constant.COMMENT_DISCUSSION.equals(comment.getLevel())){
            //
            Integer discussionId = comment.getDiscussionId();
            Discussion discussion = discussionMapper.selectById(discussionId);
            title.append("您的讨论【").append(discussion.getTitle()).append("】被回复啦！");
            Integer authorId = comment.getAuthorId();
            User user = userMapper.selectById(authorId);
            content.append(user.getUsername()).append(": ").append(comment.getContent());
            notice.setTitle(title.toString());
            notice.setContent(content.toString());
            notice.setType(Constant.REPLY_NOTICE);
            notice.setUserId(discussion.getAuthorId());
            notice.setCreateTime(new Date());
        }else if(Constant.COMMENT_REPLY.equals(comment.getLevel())){
            //评论某评论
            Integer parentId = comment.getParentId();
            Comment parentComment = commentMapper.selectById(parentId);
            title.append("你的评论【").append(parentComment.getContent()).append("】被回复啦！");
            Integer authorId = comment.getAuthorId();
            User user = userMapper.selectById(authorId);
            content.append(user.getUsername()).append(": ").append(comment.getContent());
            notice.setTitle(title.toString());
            notice.setContent(content.toString());
            notice.setType(Constant.REPLY_NOTICE);
            notice.setUserId(parentComment.getAuthorId());
            notice.setCreateTime(new Date());
        }else if(Constant.COMMENT_PERSON.equals(comment.getLevel())){
            Integer toUid = comment.getToUid();
            Integer authorId = comment.getAuthorId();
            User user = userMapper.selectById(authorId);
            title.append(user.getUsername()).append("回复了你的评论");
            content.append(user.getUsername()).append(": ").append(comment.getContent());
            notice.setTitle(title.toString());
            notice.setContent(content.toString());
            notice.setType(Constant.REPLY_NOTICE);
            notice.setUserId(toUid);
            notice.setCreateTime(new Date());
        }
        noticeMapper.insert(notice);
    }

    @Override
    public void deleteComment(Integer id) {
        commentMapper.deleteById(id);
        StringBuffer content = new StringBuffer();
        //产生通知
        Notice notice = new Notice();
        Comment comment = commentMapper.selectById(id);
        notice.setTitle("评论被删除");
        content.append("你的评论【").append("】因违反【").append("】被管理员删除");
        notice.setContent(content.toString());
        notice.setCreateTime(new Date());
        notice.setType(Constant.SYSTEM_NOTICE);
        notice.setUserId(comment.getAuthorId());
    }


}
