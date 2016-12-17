package com.trip.controller;

import com.trip.service.inter.BlogService;
import com.trip.util.MethodResourceDesc;
import com.trip.util.Result;
import com.trip.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/trip/user")
public class BlogController {
	@Autowired
	private BlogService service;
	
	@MethodResourceDesc(name="发布博客")
	@RequestMapping(value="/addBlog.do")
	@ResponseBody
	public Result insert(BlogVo vo,HttpServletRequest request){
		return service.insert(vo,request);
	}
	@MethodResourceDesc(name="添加一级评论")
	@RequestMapping(value="/commitComment.do")
	@ResponseBody
	public Result insertComment(CommentVo vo,HttpServletRequest req){
		
		return service.insertComment(vo,req);
		
	}
	
	@MethodResourceDesc(name="删除博客")
	@RequestMapping(value="/deleteBlog.do")
	@ResponseBody
	public Result delete(int id,HttpServletRequest request){
		return service.delete(id,request);
	}
	
	@MethodResourceDesc(name="搜索博客")
	@RequestMapping(value="/getblog.do")
	@ResponseBody
	public Result list(HttpServletRequest request,UserVo vo){
		return service.list(request, vo);
		
	}
	
	@MethodResourceDesc(name="搜索博客by content")
	@RequestMapping(value="/searchblog.do")
	@ResponseBody
	public Result searchblog(BlogSearchVo vo,HttpServletRequest request){
		
		return service.searchblog(vo, request);
		
	}
	
	@MethodResourceDesc(name="搜索一级评论")
	@RequestMapping(value="/getcomment.do")
	@ResponseBody
	public Result listComment(int blogId){
		//int blogid = Integer.parseInt(blogId);
		return service.listComment(blogId);
		
	}
	
	@MethodResourceDesc(name="删除博客")
	@RequestMapping(value="/addlikeit.do")
	@ResponseBody
	public Result addLikeIt(LikeitVo vo,HttpServletRequest request){
		return service.addLikeIt(vo, request);
	}
	
}
