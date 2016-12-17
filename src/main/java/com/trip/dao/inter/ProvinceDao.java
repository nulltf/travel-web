package com.trip.dao.inter;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.trip.dao.SqlMapper;

@Repository("ProvinceDao")
public interface ProvinceDao extends SqlMapper {
	public List<String> listAllProvince();

}
