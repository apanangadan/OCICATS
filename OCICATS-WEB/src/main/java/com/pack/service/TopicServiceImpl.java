package com.pack.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class TopicServiceImpl implements TopicService {

	

	@Override
	public List<String> searchFiles(String path) {
		// TODO Auto-generated method stub
		String[] rs = null;
		try {
			String pathM =path+"resources\\files";
			rs = readfile(pathM);
			System.out.println(pathM);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		List<String> files = new ArrayList<String>();
		if(rs != null){
			for(int i=0; i<rs.length;i++){
				files.add(rs[i]);
			}
		}
		
		return files;
	}

	/**
	 * 读取某个文件夹下的所有文件
	 */
	public static String[] readfile(String filepath) throws FileNotFoundException, IOException {
		String[] filelist =null;
		try {

			File file = new File(filepath);
			String absolutePath = file.getAbsolutePath();
			System.out.println(absolutePath);
			if (!file.isDirectory()) {
				System.out.println("文件");
				System.out.println("path=" + file.getPath());
				System.out.println("absolutepath=" + file.getAbsolutePath());
				System.out.println("name=" + file.getName());

			} else if (file.isDirectory()) {
				System.out.println("文件夹");
				 filelist = file.list();
				

			}

		} catch (Exception e) {
			System.out.println("readfile()   Exception:" + e.getMessage());
		}
		return filelist;
	}

	/**
	 * 删除某个文件夹下的所有文件夹和文件
	 */

	/*
	 * public static boolean deletefile(String delpath) throws
	 * FileNotFoundException, IOException { try {
	 * 
	 * File file = new File(delpath); if (!file.isDirectory()) {
	 * System.out.println("1"); file.delete(); } else if (file.isDirectory()) {
	 * System.out.println("2"); String[] filelist = file.list(); for (int i = 0;
	 * i < filelist.length; i++) { File delfile = new File(delpath + "\\" +
	 * filelist[i]); if (!delfile.isDirectory()) { System.out.println("path=" +
	 * delfile.getPath()); System.out.println("absolutepath=" +
	 * delfile.getAbsolutePath()); System.out.println("name=" +
	 * delfile.getName()); delfile.delete(); System.out.println("删除文件成功"); }
	 * else if (delfile.isDirectory()) { deletefile(delpath + "\\" +
	 * filelist[i]); } } file.delete();
	 * 
	 * }
	 * 
	 * } catch (FileNotFoundException e) { System.out.println(
	 * "deletefile()   Exception:" + e.getMessage()); } return true; }
	 */

	public static void main(String[] args) {
		//readfile("e:/videos");
		String filepath=".";
		File file = new File(filepath);
		// deletefile("D:/file");
		System.out.println("ok");
	}

	@Override
	public String LDA(String path, String defaultPath) {
		// TODO Auto-generated method stub
		TopicModel t = new TopicModel();
		String s = null;
		try {
			 s = t.topicModeling(path,defaultPath);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return s;
	}
}
