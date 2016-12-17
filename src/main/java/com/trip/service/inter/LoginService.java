package com.trip.service.inter;

import com.trip.util.Result;
import com.trip.vo.UserVo;

import javax.servlet.http.HttpServletRequest;

public interface LoginService {
	
	public Result checkUser(UserVo vo,HttpServletRequest request);

}
