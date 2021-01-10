package com.glt.service.impl;

import com.glt.base.BaseDao;
import com.glt.base.BaseServiceImpl;
import com.glt.mapper.ScMapper;
import com.glt.po.Sc;
import com.glt.service.ScService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ScServiceImpl extends BaseServiceImpl<Sc> implements ScService {

    @Autowired
    private ScMapper scMapper;

    @Override
    public BaseDao<Sc> getBaseDao() {
        return scMapper;
    }
}
