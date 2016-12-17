package com.trip.service.inter;

import com.trip.util.Result;
import com.trip.vo.*;

import javax.servlet.http.HttpServletRequest;

public interface BlogService {
	
	public Result insert(BlogVo vo,HttpServletRequest request);
	
	public Result delete(int id,HttpServletRequest request);
	//查询个人中心的朋友blog
	public Result list( HttpServletRequest request,UserVo vo);
	
	//查询某个id的blog
    public Result listSelfBlog(UserVo vo);
    
    //插入comment
    public Result insertComment(CommentVo vo,HttpServletRequest req);
    
    //查询评论
    public Result listComment(int blogId);
    
    
    //按照内容查找blog
    public Result searchblog(BlogSearchVo vo,HttpServletRequest request);
    
    //添加赞
    public Result addLikeIt(LikeitVo vo,HttpServletRequest req);


}
