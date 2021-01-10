package com.glt.service.impl;

import com.glt.base.BaseDao;
import com.glt.base.BaseServiceImpl;
import com.glt.mapper.ItemMapper;
import com.glt.po.Item;
import com.glt.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ItemServiceImpl extends BaseServiceImpl<Item> implements ItemService {

    @Autowired
    private ItemMapper itemMapper;
    @Override
    public BaseDao<Item> getBaseDao() {
        return itemMapper;
    }
}
