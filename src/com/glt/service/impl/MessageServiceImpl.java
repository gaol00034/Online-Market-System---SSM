package com.glt.service.impl;

import com.glt.base.BaseDao;
import com.glt.base.BaseServiceImpl;
import com.glt.mapper.MessageMapper;
import com.glt.po.Message;
import com.glt.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageServiceImpl extends BaseServiceImpl<Message> implements MessageService {

    @Autowired
    private MessageMapper messageMapper;

    @Override
    public BaseDao<Message> getBaseDao() {
        return messageMapper;
    }
}
