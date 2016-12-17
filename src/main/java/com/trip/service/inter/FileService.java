package com.trip.service.inter;

import com.trip.util.Result;

import java.io.InputStream;


public interface FileService {
	public Result uploadFile(String oriFileName, InputStream in, long size,String uploadFilePath) ;

}
