package com.svi.bpo.tools;

import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;

import org.apache.commons.io.FileUtils;


/**
 * @author csalazar
 *
 */
public class FileMngr {
	/**
	 * Move a file to another directory
	 * @param file
	 * @param dest
	 * @throws IOException
	 */
	public static void moveFile(File file, String dest) throws IOException{
		DirectoryMngr.mkdir(dest);
		FileUtils.moveFile(file, new File(dest+File.separator+file.getName()));
	}
	
	
	/**
	 * Move a file to another directory using a different filename
	 * <br> *added by aenriquez </br>
	 * @param file
	 * @param dest
	 * @param name
	 * @throws IOException
	 */
	public static void moveFileNewName(File file, String dest, String name) throws IOException{
		DirectoryMngr.mkdir(dest);
		File f = new File(dest+File.separator+name);
		
		System.out.println("FILE TO MOVE : " + file.getAbsolutePath());
		System.out.println("FILE DEST    : " + f.getAbsolutePath());
		
		FileUtils.moveFile(file, f);
	}
	
	
	/**
	 * Copy file to another directory
	 * @param file
	 * @param dest
	 * @throws IOException
	 */
	public static void copyFile(File file, String dest) throws IOException{
		DirectoryMngr.mkdir(dest);
		FileUtils.copyFile(file, new File(dest+File.separator+file.getName()));
	}
	
	
	/**
	 * Get file extension 
	 * @param name
	 * @return
	 */
	public static String getExtension(String name){
		String ext = name.substring(name.indexOf(".")+1);
		
		return ext;
	}
	
	/**
	 * Get file name 
	 * 
	 * <br>*added by aenriquez*</br>
	 * 
	 * @param name
	 * @return
	 */
	public static String getFileName(String name){
		name = FileMngr.remakePath(name);
		
		String fileName = name.substring(name.lastIndexOf(File.separator)+1);
		
		return fileName;
	}
	
	/**
	 * removes excess separators from path
	 * 
	 * <br>*added by aenriquez*</br>
	 * 
	 * @param path
	 * @return
	 */
	public static String remakePath(String path) {
		
		path = path.replaceAll("(\\\\+|/+)", Matcher.quoteReplacement(System.getProperty("file.separator")));
		
		return path;
	}

}
