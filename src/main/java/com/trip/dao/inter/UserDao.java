package com.trip.dao.inter;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.trip.dao.SqlMapper;
import com.trip.vo.FollowVo;
import com.trip.vo.FollowerVo;
import com.trip.vo.UserVo;
import com.trip.vo.searchVo;


@Repository("userDao")
public interface UserDao extends SqlMapper {
	
	public UserVo checkUser(UserVo vo);
	
	public void register(UserVo vo);
	
	public void upload_head(UserVo vo);
	
	public int blogCount(int id);
	
	public int followerCount(int id);
	
	public int fansCount(int id);
	
	public List<FollowerVo>  listFollower(int id);
	
	public List<FollowerVo> listFans(int id);
	
	public UserVo getPersonById(int id);
	
	public int editInfo(UserVo vo);
	
	public List<UserVo> searchUser(searchVo vo);
	
	public int  addFollow(FollowVo vo);
	
	public int  deletefollowOrFans(FollowVo vo);
	
	public int checkUserIsExit(String userAccount );

}
