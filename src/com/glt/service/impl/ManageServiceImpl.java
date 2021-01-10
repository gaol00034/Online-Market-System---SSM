package com.glt.service.impl;

import com.glt.base.BaseDao;
import com.glt.base.BaseServiceImpl;
import com.glt.mapper.ManageMapper;
import com.glt.po.Manage;
import com.glt.service.ManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ManageServiceImpl extends BaseServiceImpl<Manage> implements ManageService {
    @Autowired
    ManageMapper manageMapper;

    @Override
    public BaseDao<Manage> getBaseDao() {
        return manageMapper;
    }
}
