package com.trip.service.inter;

import javax.servlet.http.HttpServletRequest;

import com.trip.util.Result;
import com.trip.vo.FollowVo;
import com.trip.vo.UserVo;
import com.trip.vo.searchVo;

public interface UserService {
	//注册
	public Result register(UserVo vo);
	
	//搜索个人
	public Result searchUser(searchVo vo,HttpServletRequest request);
	//上传用户图片
	public Result upload_head(String photoUrl,HttpServletRequest request);
	
	//查询我的关注，粉丝，blog数量
	
	public  Result search_personalCount(int id);
		
	//查询我关注的人
	public Result  listFollower(int id);
	
	//查询我的粉丝
	public Result listFans(int id);
	
	//根据id查个人信息
	public Result getPersonById(HttpServletRequest request);
	
	//修改我的个人信息
	public Result editInfo(UserVo user ,HttpServletRequest request);
	
	//加关注
	public Result addFollow(FollowVo FollowVo ,HttpServletRequest request);
	
	//取关
	public Result deletefollowOrFans(FollowVo FollowVo ,HttpServletRequest request);
	

}
