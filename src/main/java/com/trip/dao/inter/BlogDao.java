package com.trip.dao.inter;

import com.trip.dao.SqlMapper;
import com.trip.vo.*;
import org.springframework.stereotype.Repository;

import java.util.List;
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
