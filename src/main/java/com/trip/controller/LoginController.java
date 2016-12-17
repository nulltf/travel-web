package com.trip.controller;

import com.trip.service.inter.LoginService;
import com.trip.util.Result;
import com.trip.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
@Controller
@Scope("prototype")
public class LoginController {
	@Autowired
	private LoginService service;
	
	@RequestMapping(value = "/trip/user/login.do")
	@ResponseBody
	public Result login(UserVo vo,HttpServletRequest request){
		return service.checkUser(vo,request);
	}

	
	
	/**
	 * ============================== * jsp页面的jspx forward跳转 * ==========================================
	 */
	
	@RequestMapping(value="/trip/user/login.jspx")
	public ModelAndView _toLoginPage(){
		return new ModelAndView("forward:/login.jsp");
	}
}

