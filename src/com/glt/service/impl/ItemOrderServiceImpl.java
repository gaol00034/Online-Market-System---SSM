package com.glt.service.impl;

import com.glt.base.BaseDao;
import com.glt.base.BaseServiceImpl;
import com.glt.mapper.ItemOrderMapper;
import com.glt.po.ItemOrder;
import com.glt.service.ItemOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ItemOrderServiceImpl extends BaseServiceImpl<ItemOrder> implements ItemOrderService {

    @Autowired
    private ItemOrderMapper itemOrderMapper;

    @Override
    public BaseDao<ItemOrder> getBaseDao() {
        return itemOrderMapper;
    }
}
