package com.trip.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trip.dao.inter.ProvinceDao;
import com.trip.service.inter.ProvinceService;
import com.trip.util.Result;
@Service
public class ProvinceServiceImpl implements ProvinceService {
    @Autowired
	ProvinceDao dao;
	@Override
	public Result listAllProvince() {
		Result rs = new Result();
		rs.setSuccess(true);
		rs.setObject(dao.listAllProvince());
		return rs;	
	}

}
