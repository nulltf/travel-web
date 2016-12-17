package com.trip.service;

import com.trip.dao.inter.CityDao;
import com.trip.service.inter.CityService;
import com.trip.util.Result;
import com.trip.util.ResultCode;
import com.trip.vo.CitySearchVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class CityServiceImpl  extends BaseService implements CityService {

	@Autowired
	private CityDao dao;
	
	@Override
	public Result listAllCity(CitySearchVo vo) {
		return getResult(ResultCode.SUCCESS,dao.listAllCity(vo),"success");
		 
		 
	}

}
