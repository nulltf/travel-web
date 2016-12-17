package com.trip.service;

import com.trip.dao.inter.UserDao;
import com.trip.service.inter.LoginService;
import com.trip.util.Result;
import com.trip.util.ResultCode;
import com.trip.util.SysConst;
import com.trip.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
@Service
public class LoginServiceImpl extends BaseService implements LoginService {

	@Autowired
	private UserDao dao;
	@Override
	public Result checkUser(UserVo vo,HttpServletRequest request) {
		HttpSession session = request.getSession();
		UserVo resultvo = dao.checkUser(vo);
		if(resultvo == null){
			return getResult(ResultCode.FAILURE, "", "failed");
		}
		else{
			session.setAttribute(SysConst.CurrentUser, resultvo);
			return getResult(ResultCode.SUCCESS, resultvo, "success");
		}
		
	}

}
