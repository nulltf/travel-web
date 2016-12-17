package com.trip.service.inter;

import javax.servlet.http.HttpServletRequest;

import com.trip.util.Result;
import com.trip.vo.UserVo;

public interface LoginService {
	
	public Result checkUser(UserVo vo,HttpServletRequest request);

}
