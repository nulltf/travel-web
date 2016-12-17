package com.trip.dao.inter;

import com.trip.dao.SqlMapper;
import com.trip.vo.CitySearchVo;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository("CityDao")
public interface CityDao extends SqlMapper{
	
	public List<String> listAllCity(CitySearchVo vo);

}
