package com.trip.service.inter;

import java.io.InputStream;

import com.trip.util.Result;


public interface FileService {
	public Result uploadFile(String oriFileName, InputStream in, long size,String uploadFilePath) ;

}
