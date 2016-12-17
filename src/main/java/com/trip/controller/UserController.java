package com.trip.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.trip.service.inter.UserService;
import com.trip.util.MethodResourceDesc;
import com.trip.util.Result;
import com.trip.util.SysConst;
import com.trip.vo.FollowVo;
import com.trip.vo.UserVo;
import com.trip.vo.searchVo;

@Controller
public class UserController {
	
	HttpSession session;
	@Autowired
	UserService service ;
	@MethodResourceDesc(name="注册用户")
	@RequestMapping(value="/trip/user/register.do")
	@ResponseBody
	public Result register(UserVo vo){
		return service.register(vo);
		
	}
	
	@MethodResourceDesc(name="上传用户头像")
	@RequestMapping(value="/trip/user/upload_head.do")
	@ResponseBody
	public Result upload_head(String photoUrl,HttpServletRequest request){
		return service.upload_head(photoUrl,request);
	}
	
	
	@MethodResourceDesc(name="查询个人信息的数量")
	@RequestMapping(value="/trip/user/personalCount.do")
	@ResponseBody
	public Result search_personalCount(HttpServletRequest request){
		session = request.getSession();
		int id =((UserVo) session.getAttribute(SysConst.CurrentUser)).getId();
		return service.search_personalCount(id);
	}
	@MethodResourceDesc(name="根据id查询个人信息")
	@RequestMapping(value="/trip/user/getPersonById.do")
	@ResponseBody
	public Result getPersonById(HttpServletRequest request){
		return service.getPersonById(request);
	}
	@MethodResourceDesc(name="查询我关注的人")
	@RequestMapping(value="/trip/user/listFollower.do")
	@ResponseBody
	public Result listFollower(int id){
		return service.listFollower(id);
	}
	
	@MethodResourceDesc(name="查询我的粉丝")
	@RequestMapping(value="/trip/user/listFans.do")
	@ResponseBody
	public Result listFans(int id){
		return service.listFans(id);
	}
	
	@MethodResourceDesc(name="修改资料")
	@RequestMapping(value="/trip/user/editInfo.do")
	@ResponseBody
	public Result editInfo(UserVo user,HttpServletRequest request){
		return service.editInfo(user, request);
	}
	
	@MethodResourceDesc(name="搜索个人")
	@RequestMapping(value="/trip/user/searchUser.do")
	@ResponseBody
	public Result searchUser(searchVo vo,HttpServletRequest request){
		return service.searchUser(vo,request);
	}
	
	@MethodResourceDesc(name="加关注")
	@RequestMapping(value="/trip/user/addfollow.do")
	@ResponseBody
	public Result addFollow(FollowVo vo,HttpServletRequest request ){
		return service.addFollow(vo,request);
	}
	
	@MethodResourceDesc(name="取消关注与移除粉丝")
	@RequestMapping(value="/trip/user/deletefollowOrFans.do")
	@ResponseBody
	public Result deletefollowOrFans(FollowVo vo,HttpServletRequest request ){
		return service.deletefollowOrFans(vo,request);
	}
	
	
	
	
	/**
	 * 
	 * jspx页面
	 * */
	@MethodResourceDesc(name="修改个人信息页面")
	@RequestMapping(value="/trip/user/editInfo.jspx")
	public ModelAndView _toEditInfo(){
		return new ModelAndView("forward:/editInfo.jsp");
	}
	
	@MethodResourceDesc(name="主页")
	@RequestMapping(value="/trip/user/index.jspx")
	public ModelAndView _toIndex(){
		return new ModelAndView("forward:/index.jsp");
	}


	@MethodResourceDesc(name="注册页面")
	@RequestMapping(value="/trip/user/register.jspx")
	public ModelAndView _toregister(){
		return new ModelAndView("forward:/register.jsp");
	}
	
	@MethodResourceDesc(name= "我关注的页面")
	@RequestMapping(value="/trip/user/follower.jspx")
	public ModelAndView _tofollower(HttpServletRequest request){
		Map<String, String> map = new HashMap<String, String>();
		map.put("id", request.getParameter("id"));
		return new ModelAndView("forward:/follower.jsp",map);
		
	}
	
	@MethodResourceDesc(name="我的粉丝页面")
	@RequestMapping(value="/trip/user/fans.jspx")
	public ModelAndView _tofans(HttpServletRequest request){
		Map<String, String> map = new HashMap<String, String>();
		map.put("id", request.getParameter("id"));
		return new ModelAndView("forward:/fans.jsp",map);
	}
}
