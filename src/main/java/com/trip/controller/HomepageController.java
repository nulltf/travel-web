package com.trip.controller;

import com.trip.service.inter.BlogService;
import com.trip.service.inter.UserService;
import com.trip.util.MethodResourceDesc;
import com.trip.util.Result;
import com.trip.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
public class HomepageController {
	@Autowired
	BlogService service;
	@Autowired
	UserService userService;
	@MethodResourceDesc(name="个人博客信息")
	@RequestMapping(value="/trip/user/homepage/blogdata.do")
	@ResponseBody
	public Result listSelfBlog(UserVo vo){
		return service.listSelfBlog(vo);
	}
	
	@MethodResourceDesc(name="查找博客，关注的人，粉丝的数目")
	@RequestMapping(value="/trip/user/homepage/personalCount.do")
	@ResponseBody
	public Result personalCount(int id){
		return userService.search_personalCount(id);	
	} 
	/**
	 * 
	 *jspx跳转 
	 * 
	 * **/
	@MethodResourceDesc(name="个人主页")
	@RequestMapping(value="/trip/user/homepage.jspx")
	public ModelAndView _tohomepage(HttpServletRequest request){
		Map<String, String> map = new HashMap<String, String>();
		map.put("id", request.getParameter("id"));
		return new ModelAndView("forward:/homepage.jsp",map);
	}
	
	@MethodResourceDesc(name="我关注的人")
	@RequestMapping(value="/trip/user/homepage/follower.jspx")
	public ModelAndView _tofollowPage(HttpServletRequest request){
		Map<String, String> map = new HashMap<String, String>();
		map.put("id", request.getParameter("id"));
		return new ModelAndView("forward:/follower.jsp",map);
	}

	@MethodResourceDesc(name="我的粉丝")
	@RequestMapping(value="/trip/user/homepage/fans.jspx")
	public ModelAndView _tofansPage(HttpServletRequest request){
		Map<String, String> map = new HashMap<String, String>();
		map.put("id", request.getParameter("id"));
		return new ModelAndView("forward:/fans.jsp",map);
	}

}


