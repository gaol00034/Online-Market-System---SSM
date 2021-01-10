package com.glt.service.impl;

import com.glt.base.BaseDao;
import com.glt.base.BaseServiceImpl;
import com.glt.mapper.CommentMapper;
import com.glt.po.Comment;
import com.glt.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl extends BaseServiceImpl<Comment> implements CommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Override
    public BaseDao<Comment> getBaseDao() {
        return commentMapper;
    }
}
