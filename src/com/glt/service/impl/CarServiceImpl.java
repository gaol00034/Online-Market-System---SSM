package com.glt.service.impl;

import com.glt.base.BaseDao;
import com.glt.base.BaseServiceImpl;
import com.glt.mapper.CarMapper;
import com.glt.po.Car;
import com.glt.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CarServiceImpl extends BaseServiceImpl<Car> implements CarService {

    @Autowired
    private CarMapper carMapper;

    @Override
    public BaseDao<Car> getBaseDao() {
        return carMapper;
    }
}
