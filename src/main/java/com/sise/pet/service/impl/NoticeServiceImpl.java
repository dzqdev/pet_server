package com.sise.pet.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sise.pet.entity.Notice;
import com.sise.pet.mapper.NoticeMapper;
import com.sise.pet.service.INoticeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 通知 服务实现类
 * </p>
 *
 * @author author
 * @since 2020-03-12
 */
@Service
public class NoticeServiceImpl extends ServiceImpl<NoticeMapper, Notice> implements INoticeService {

    @Resource
    private NoticeMapper noticeMapper;

    @Override
    public List<Notice> getUserNoticesByType(Integer userId, String type) {
        QueryWrapper<Notice> queryWrapper = new QueryWrapper<>();
        if(StringUtils.equals("read",type)){
            queryWrapper.isNotNull("readTime");
        }else{
            queryWrapper.isNull("readTime");
        }
        List<Notice> list = noticeMapper.selectList(queryWrapper);
        return list;
    }
}
