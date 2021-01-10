package com.glt.service.impl;

import com.glt.base.BaseDao;
import com.glt.base.BaseServiceImpl;
import com.glt.mapper.ItemCategoryMapper;
import com.glt.po.ItemCategory;
import com.glt.service.ItemCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ItemCategoryServiceImpl extends BaseServiceImpl<ItemCategory> implements ItemCategoryService {
    @Autowired
    ItemCategoryMapper itemCategoryMapper;

    @Override
    public BaseDao<ItemCategory> getBaseDao() {
        return itemCategoryMapper;
    }
}
