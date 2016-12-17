package com.trip.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.trip.service.inter.BlogService;
import com.trip.util.MethodResourceDesc;
import com.trip.util.Result;
import com.trip.vo.BlogSearchVo;
import com.trip.vo.BlogVo;
import com.trip.vo.CommentVo;
import com.trip.vo.LikeitVo;
import com.trip.vo.UserVo;

@Controller
public class BlogController {
	@Autowired
	private BlogService service;
	
	@MethodResourceDesc(name="发布博客")
	@RequestMapping(value="/trip/user/addBolg.do")
	@ResponseBody
	public Result insert(BlogVo vo,HttpServletRequest request){
		return service.insert(vo,request);
	}
	@MethodResourceDesc(name="添加一级评论")
	@RequestMapping(value="/trip/user/commitComment.do")
	@ResponseBody
	public Result insertComment(CommentVo vo,HttpServletRequest req){
		
		return service.insertComment(vo,req);
		
	}
	
	@MethodResourceDesc(name="删除博客")
	@RequestMapping(value="/trip/user/deleteBolg.do")
	@ResponseBody
	public Result delete(int id,HttpServletRequest request){
		return service.delete(id,request);
	}
	
	@MethodResourceDesc(name="搜索博客")
	@RequestMapping(value="/trip/user/getblog.do")
	@ResponseBody
	public Result list(HttpServletRequest request,UserVo vo){
		return service.list(request, vo);
		
	}
	
	@MethodResourceDesc(name="搜索博客by content")
	@RequestMapping(value="/trip/user/searchblog.do")
	@ResponseBody
	public Result searchblog(BlogSearchVo vo,HttpServletRequest request){
		
		return service.searchblog(vo, request);
		
	}
	
	@MethodResourceDesc(name="搜索一级评论")
	@RequestMapping(value="/trip/user/getcomment.do")
	@ResponseBody
	public Result listComment(int blogId){
		//int blogid = Integer.parseInt(blogId);
		return service.listComment(blogId);
		
	}
	
	@MethodResourceDesc(name="删除博客")
	@RequestMapping(value="/trip/user/addlikeit.do")
	@ResponseBody
	public Result addLikeIt(LikeitVo vo,HttpServletRequest request){
		return service.addLikeIt(vo, request);
	}
	
}
