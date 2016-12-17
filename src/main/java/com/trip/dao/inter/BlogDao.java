package com.trip.dao.inter;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.trip.dao.SqlMapper;
import com.trip.vo.BlogSearchVo;
import com.trip.vo.BlogVo;
import com.trip.vo.CommentVo;
import com.trip.vo.LikeitVo;
import com.trip.vo.UserVo;
@Repository("blogDao")
public interface BlogDao extends SqlMapper {
	
	public void insert(BlogVo vo);
	
	public void delete(int id);
	
	public List<BlogVo> list(UserVo uservo);
	
	public List<BlogVo> listSelfBlog(UserVo uservo);
	
	public int insertComment(CommentVo vo);
	
	public List<CommentVo> listComment(int blogId);
	
	public List<BlogVo> searchblog(BlogSearchVo vo);
	
	public int addlikeit(LikeitVo vo);
	
	public int deletelikeit(LikeitVo vo);
	
	public int checklikeit(LikeitVo vo);

}
