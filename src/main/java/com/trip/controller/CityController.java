package com.trip.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.trip.service.inter.CityService;
import com.trip.util.Result;
import com.trip.vo.CitySearchVo;

@Controller
public class CityController {
	@Autowired
	private CityService service;
	@RequestMapping(value = "/trip/listAllCity.do")
	@ResponseBody
	public Result listAllCtiy(CitySearchVo vo){
		return service.listAllCity(vo);
		
		
	}

}
