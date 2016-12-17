package com.trip.dao.inter;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.trip.dao.SqlMapper;
import com.trip.vo.CitySearchVo;
@Repository("CityDao")
public interface CityDao extends SqlMapper{
	
	public List<String> listAllCity(CitySearchVo vo);

}
