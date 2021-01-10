package com.glt.service.impl;

import com.glt.base.BaseDao;
import com.glt.base.BaseServiceImpl;
import com.glt.mapper.NewsMapper;
import com.glt.po.News;
import com.glt.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NewsServiceImpl extends BaseServiceImpl<News> implements NewsService {

    @Autowired
    private NewsMapper newsMapper;

    @Override
    public BaseDao<News> getBaseDao() {
        return newsMapper;
    }
}
