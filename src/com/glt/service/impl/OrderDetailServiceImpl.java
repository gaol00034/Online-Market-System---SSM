package com.glt.service.impl;

import com.glt.base.BaseDao;
import com.glt.base.BaseServiceImpl;
import com.glt.mapper.OrderDetailMapper;
import com.glt.po.OrderDetail;
import com.glt.service.OrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderDetailServiceImpl extends BaseServiceImpl<OrderDetail> implements OrderDetailService {
    @Autowired
    private OrderDetailMapper orderDetailMapper;

    @Override
    public BaseDao<OrderDetail> getBaseDao() {
        return orderDetailMapper;
    }
}
