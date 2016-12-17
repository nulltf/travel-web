package com.trip.service;

import com.trip.dao.inter.BlogDao;
import com.trip.service.inter.BlogService;
import com.trip.util.Result;
import com.trip.util.ResultCode;
import com.trip.util.SysConst;
import com.trip.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;
@Service
public class BlogServiceImpl extends BaseService implements BlogService{

	@Autowired
	private BlogDao blogDao;
	private HttpSession session ;
	
	@Override
	public Result insert(BlogVo vo , HttpServletRequest request) {
	    session = request.getSession();
		UserVo userVo = (UserVo) session.getAttribute(SysConst.CurrentUser);
		vo.setUserid(userVo.getId());
		vo.setCreatetime(new Date());
		blogDao.insert(vo);
		return getResult(ResultCode.SUCCESS,"", "博文发布成功");
	}

	@Override
	public Result delete(int id, HttpServletRequest request) {
		blogDao.delete(id);	
		return getResult(ResultCode.SUCCESS,"", "博文删除成功");
			
	}

	@Override
	public Result list( HttpServletRequest request ,UserVo uservo) {
		session = request.getSession();
		UserVo vo = (UserVo) session.getAttribute(SysConst.CurrentUser);
		uservo.setId(vo.getId());
		List<BlogVo> list = blogDao.list(uservo);
		for(int i=0;i<list.size();i++){
			list.get(i).setLocaltime(list.get(i).getCreatetime().toLocaleString());
		}
		return getResult(ResultCode.SUCCESS, list, "");
	}

	@Override
	public Result listSelfBlog(UserVo vo) {
		List<BlogVo> list = blogDao.listSelfBlog(vo);
		for(int i=0;i<list.size();i++){
			list.get(i).setLocaltime(list.get(i).getCreatetime().toLocaleString());
		}
		return getResult(ResultCode.SUCCESS, list, "");
	}

	@Override
	public Result insertComment(CommentVo vo,HttpServletRequest req) {
		session = req.getSession();
		UserVo uservo = (UserVo) session.getAttribute(SysConst.CurrentUser);
		vo.setUserId(uservo.getId());
		vo.setCreateTime(new Date().toLocaleString());
		int i =blogDao.insertComment(vo);
		
		if(i>0){
			return getResult(ResultCode.SUCCESS, "", "");
		}else{
			return getResult(ResultCode.FAILURE, "", "");
		}
	}

	@Override
	public Result listComment(int blogId) {
		List<CommentVo> list = blogDao.listComment(blogId);
		return getResult(ResultCode.SUCCESS, list, "");
	}

	@Override
	public Result searchblog(BlogSearchVo vo,HttpServletRequest request) {
		vo.setSize(5);
		session = request.getSession();
		UserVo uservo = (UserVo) session.getAttribute(SysConst.CurrentUser);
		vo.setId(uservo.getId());
		List<BlogVo> list = blogDao.searchblog(vo);
		for(int i=0;i<list.size();i++){
			list.get(i).setLocaltime(list.get(i).getCreatetime().toLocaleString());
		}
		return getResult(ResultCode.SUCCESS, list, "");
	}

	@Override
	public Result addLikeIt(LikeitVo vo, HttpServletRequest req) {
		session = req.getSession();
		UserVo uservo = (UserVo) session.getAttribute(SysConst.CurrentUser);
		vo.setUserId(uservo.getId());
		int check = blogDao.checklikeit(vo);
		if(check>0){
			blogDao.deletelikeit(vo);
		    return getResult(ResultCode.FAILURE, "", "取消赞成功");
		}else{
			int i = blogDao.addlikeit(vo);
			
			if(i>0){
				return getResult(ResultCode.SUCCESS, "", "点赞成功");
			}else{
				return getResult(ResultCode.FAILURE, "", "");
			}
		}
	
	}

}
