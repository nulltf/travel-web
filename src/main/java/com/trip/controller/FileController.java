package com.trip.controller;

import com.trip.service.inter.FileService;
import com.trip.util.MethodResourceDesc;
import com.trip.util.Result;
import com.trip.util.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Iterator;

@Controller
public class FileController {
	@Autowired
	FileService fileService;
	@MethodResourceDesc(name="头像上传")
	@ResponseBody
	@RequestMapping(value="/trip/upload_head.do")
	public Result uploadFile(HttpServletRequest request){
		Result result = new Result();
		result.setCode(ResultCode.FAILURE);
		result.setMessage(null);
		MultipartHttpServletRequest  multipartRequest = (MultipartHttpServletRequest) request;
		Iterator iter = multipartRequest.getFileNames();
		InputStream in = null;
		try {
			while(iter.hasNext()){
				String param = (String) iter.next();
				CommonsMultipartFile file = (CommonsMultipartFile) multipartRequest.getFile(param);
				in = file.getFileItem().getInputStream();
				 String oriFileName = file.getOriginalFilename();
				 result = fileService.uploadFile(oriFileName, in, file.getSize(),"/head");
				 in.close();	
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			if(in!=null)
				try{
					in.close();}
			catch(Exception e){
				e.printStackTrace();
			}
		}
		return result;
	}
	
	@MethodResourceDesc(name="blog图像上传")
	@ResponseBody
	@RequestMapping(value="/trip/uploadBlogPhoto.do")
	public Result uploadBlogPhoto(HttpServletRequest request){
		Result result = new Result();
		result.setCode(ResultCode.FAILURE);
		result.setMessage(null);
		MultipartHttpServletRequest  multipartRequest = (MultipartHttpServletRequest) request;
		Iterator iter = multipartRequest.getFileNames();
		InputStream in = null;
		try {
			while(iter.hasNext()){
				String param = (String) iter.next();
				CommonsMultipartFile file = (CommonsMultipartFile) multipartRequest.getFile(param);
				in = file.getFileItem().getInputStream();
				 String oriFileName = file.getOriginalFilename();
				 result = fileService.uploadFile(oriFileName, in, file.getSize(),"/blog");
				 in.close();	
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			if(in!=null)
				try{
					in.close();}
			catch(Exception e){
				e.printStackTrace();
			}
		}
		return result;
	}
	@MethodResourceDesc(name = "查看图片文件")
	@RequestMapping(value = "/trip/imgFileView.do")
	@ResponseBody	
	public void imgFileView(HttpServletRequest request,HttpServletResponse response,String fileName){
		
		ServletOutputStream stream 	=  null;
		BufferedInputStream fif =  null;
		try {
			File file = new File(fileName);
			if(!file.exists()){
				return;
			}
			stream = response.getOutputStream();
			response.reset();
			response.setContentType("image/jpeg");
			response.setHeader("Content-Length", String.valueOf(file.length()));
			fif = new BufferedInputStream(new FileInputStream(file));
			int d;
			byte[] buf = new byte[10240];
			while ((d = fif.read(buf)) != -1) {
				stream.write(buf, 0, d);
			}
			stream.flush();
		} catch (Exception e) {
			e.printStackTrace();
			try {
				if (stream != null) {
					stream.close();
				}
				if (fif != null) {
					fif.close();
				}
				
			} catch (Exception e11) {
			}
		}
	}

}
