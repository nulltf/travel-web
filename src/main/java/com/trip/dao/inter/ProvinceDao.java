package com.trip.dao.inter;

import com.trip.dao.SqlMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("ProvinceDao")
public interface ProvinceDao extends SqlMapper {
	public List<String> listAllProvince();

}
