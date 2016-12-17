package com.trip.util;

import java.io.*;


public class FileUtil {
	/**
	 * UTF8编码写入文件
	 * 
	 * @param filename
	 * @param str
	 * @throws IOException
	 */
	public static void writIn(String filename, String str) throws IOException {
		writIn(filename, str, "UTF-8");
	}

	/**
	 * 创建一个目录
	 */
	public static boolean mkDir(String DirectoryName) {
		boolean bRet = false;
		java.io.File file = new java.io.File(DirectoryName);
		if (!file.exists() && file.mkdirs()) {
			bRet = true;
		}
		return bRet;
	}

	/**
	 * 往一个指定文件里全新写入指定编码字符串
	 * 
	 * @param filename
	 * @param str
	 * @param strCode
	 *            编码，如GBK
	 * @throws IOException
	 */
	public static void writIn(String filename, String str, String strCode)
			throws IOException {
		FileWriter fw = null;
		BufferedWriter bw = null;
		try {
			mkDir(filename.substring(0, filename.lastIndexOf(File.separator)));
			fw = new FileWriter(filename); // 建立FileWrite变量,并设定由fw变量变数引用
			bw = new BufferedWriter(fw);
			// 建立BufferedWriter变量,并设定由bw变量变数引用
			// 将字串写入文件
			bw.write(new String(str.getBytes(strCode)));
			bw.flush(); // 将资料更新至文件
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (fw != null)
				fw.close(); // 关闭档案
			if (bw != null)
				bw.close();
		}
	}

	/**
	 * 读取文件
	 * 
	 * @param filename
	 * @return
	 * @throws IOException
	 */
	public static String readNoNewLine(String filename) {
		InputStreamReader read = null;
		BufferedReader reader = null;
		String line = "";
		StringBuffer readfile = new StringBuffer();
		try {
			File file = new File(filename);
			read = new InputStreamReader(new FileInputStream(file), "utf-8");
			reader = new BufferedReader(read);
			while ((line = reader.readLine()) != null) {
				readfile.append(line + "\r");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		} finally {
			try {
				if (read != null)
					read.close();
				if (reader != null)
					reader.close();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		return readfile.toString();
	}

	/**
	 * 删除一个指定的文件
	 */
	public static boolean deleteFile(String FileName) {
		boolean bRet = false;
		if (FileName != null && FileName.length() > 0) {
			java.io.File filename = new java.io.File(FileName);
			if (filename.delete()) {
				bRet = true;
			}
		}
		return bRet;
	}

	/**
	 * 创建文件夹
	 * 
	 * @param destDir
	 */
	public static void mkdirs(String destDir) {
		File dir = new File(destDir);
		if (!dir.exists()) {
			dir.mkdirs();
		}
	}

	/**
	 * 拷贝文件
	 * 
	 * @param sourceFile
	 * @param destDir
	 * @param newFileName
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static String copyFile(String sourceFile, String destDir,
			String newFileName) throws FileNotFoundException, IOException {
		return copyFile(new FileInputStream(sourceFile), destDir, newFileName);
	}

	/**
	 * 拷贝文件
	 * 
	 * @param source
	 * @param destDir
	 * @param newFileName
	 * @return
	 * @throws IOException
	 */
	public static String copyFile(InputStream source, String destDir,
			String newFileName) throws IOException {
		File dir = new File(destDir);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		if (!dir.isDirectory()) {
			throw new IOException("dest dir (" + destDir + ") is not a folder");
		}
		String destFileFullName = null;
		BufferedOutputStream out = null;
		try {
			destFileFullName = destDir + File.separator + newFileName;
			out = new BufferedOutputStream(new FileOutputStream(
					destFileFullName));
			byte[] buffer = new byte[8192];
			int bytesRead = 0;
			while ((bytesRead = source.read(buffer, 0, 8192)) != -1) {
				out.write(buffer, 0, bytesRead);
			}

		} finally {
			if (out != null) {
				out.close();
			}
		}
		return destFileFullName;
	}
	/**
	 * 获取文件的后缀，没有后缀的直接返回空字符
	 * @param fileName
	 * @return
	 */
	public static String getFilePrex(String fileName){
		int index = fileName.lastIndexOf(".");
		if(index==-1)
			return "";
		return fileName.substring(index);
	}
	
	public static void main(String[] args){
		System.out.println(FileUtil.readNoNewLine("D:/test/server_queue.ini"));
	}

}
