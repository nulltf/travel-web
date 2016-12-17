package com.trip.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trip.dao.inter.UserDao;
import com.trip.service.inter.UserService;
import com.trip.util.Result;
import com.trip.util.ResultCode;
import com.trip.util.SysConst;
import com.trip.vo.FollowVo;
import com.trip.vo.FollowerVo;
import com.trip.vo.UserVo;
import com.trip.vo.searchVo;
@Service
public class UserServiceImpl extends BaseService implements UserService {
	@Autowired
	private UserDao dao;
	
	private int id;
	
	private int blogCount;
	
	private int followerCount;
	
	private int fansCount;
	
	//注册
	@Override
	public Result register(UserVo vo){
		int i = dao.checkUserIsExit(vo.getUserAccount());
		if(i>0){
			return getResult(ResultCode.FAILURE,"", "已存在"+vo.getUserAccount()+"用户");
		}
		vo.setCreateTime(new Date());
		vo.setPhotoUrl("/resource/img/head.jpg");
		dao.register(vo);
	   return getResult(ResultCode.SUCCESS,"", "success");
	}
	//上传头像
	@Override
	public Result upload_head(String photoUrl,HttpServletRequest request) {
		HttpSession session = request.getSession() ;
		UserVo vo = (UserVo) session.getAttribute(SysConst.CurrentUser);
		session.setAttribute(SysConst.CurrentUser, vo);
		vo.setPhotoUrl(photoUrl);
		dao.upload_head(vo);
		return getResult(ResultCode.SUCCESS, "", "更新成功");
		
	}
	//查询个人的关注，粉丝，blog数量
	@Override
	public Result search_personalCount(int id) {
		Map<String,Integer> map = new HashMap<String, Integer>();
		blogCount = dao.blogCount(id);
		followerCount = dao.followerCount(id);
		fansCount = dao.fansCount(id);
		map.put("blogCount", blogCount);
		map.put("followerCount", followerCount);
		map.put("fansCount", fansCount);
		return getResult(ResultCode.SUCCESS, map, "");
	}
	@Override
	public Result listFollower(int id) {
//		HttpSession session = request.getSession();
//		UserVo vo = (UserVo) session.getAttribute(SysConst.CurrentUser);
//		id = vo.getId();
		List<FollowerVo> list =dao.listFollower(id);
		return getResult(ResultCode.SUCCESS, list, "");
	}
	@Override
	public Result listFans(int id) {
		
		List<FollowerVo> list =dao.listFans(id);
		return getResult(ResultCode.SUCCESS, list, "");
	}
	@Override
	public Result getPersonById(HttpServletRequest request) {
		HttpSession session = request.getSession() ;
		UserVo uservo = (UserVo) session.getAttribute(SysConst.CurrentUser);
		int id = uservo.getId();
		UserVo vo =dao.getPersonById(id); 
		return getResult(ResultCode.SUCCESS, vo, "");
	}
	@Override
	public Result editInfo(UserVo user, HttpServletRequest request) {
		HttpSession session = request.getSession() ;
		UserVo vo = (UserVo) session.getAttribute(SysConst.CurrentUser);
		int id = vo.getId();
		user.setId(id);
		int i = dao.editInfo(user);
		if (i>0) {
			return getResult(ResultCode.SUCCESS, "资料更新成功", "");
		}else{
			return getResult(ResultCode.FAILURE, "资料更新失败", "");
		}
		
	}
	@Override
	public Result searchUser(searchVo vo,HttpServletRequest request) {
		HttpSession session = request.getSession() ;
		UserVo uservo = (UserVo) session.getAttribute(SysConst.CurrentUser);
		int id = uservo.getId();
		vo.setUserid(id);
		List<UserVo> list = dao.searchUser(vo);
		return getResult(ResultCode.SUCCESS, list, "");
	}
	@Override
	public Result addFollow(FollowVo followVo, HttpServletRequest request) {
		HttpSession session = request.getSession() ;
		UserVo vo = (UserVo) session.getAttribute(SysConst.CurrentUser);
		String  id = vo.getId()+"";
		followVo.setFans(id);
		followVo.setDate_(new Date().toLocaleString());
		int i = dao.addFollow(followVo);
		if(i>0){
			return getResult(ResultCode.SUCCESS, "", "");
		}
		else{
			return getResult(ResultCode.FAILURE, "", "");
		}
		
	}
	@Override
	public Result deletefollowOrFans(FollowVo followVo,
			HttpServletRequest request) {
		
		HttpSession session = request.getSession() ;
		UserVo vo = (UserVo) session.getAttribute(SysConst.CurrentUser);
		String  id = vo.getId()+"";
		if(followVo.getType().equals("1")){
			followVo.setFans(id);
		}else{
			System.out.println(followVo.getFans());
			followVo.setFollower(id);
		}
		
		followVo.setDate_(new Date().toLocaleString());
		int i = dao.deletefollowOrFans(followVo);
		if(i>0){
			return getResult(ResultCode.SUCCESS, "", "");
		}
		else{
			return getResult(ResultCode.FAILURE, "", "");
		}
		
	}
	

}
