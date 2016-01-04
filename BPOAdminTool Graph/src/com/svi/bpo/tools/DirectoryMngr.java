package com.svi.bpo.tools;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Queue;

import com.google.common.io.Files;
import com.svi.bpo.constants.ImageFormat;


/**
 * @author csalazar
 *
 */
public class DirectoryMngr{

	/**
	 * @param dir
	 */
	public static String mkdir(String dir) {
		File dirFile = new File(dir);
		if(!dirFile.exists()){			
			dirFile.mkdirs();
		}
		
		return dir;
	}
	
	/**
	 * @param dir
	 * @return
	 */
	public static int getFileCount(String dir){
		File dirFile = new File(dir);
		if(dirFile.isFile())
			return 1;
		else if(dirFile.isDirectory()){
			File [] fileLst = dirFile.listFiles();
			return fileLst.length;
		}
		
		return 0;
	}
	
	/**
	 * @param dir
	 */
	public static void delCntnts(String dir) {
		delFldrCntnts(new File(dir));
	}	
	
	/**
	 * @param folder
	 */
	private  static void delFldrCntnts(File folder) {
	    File[] files = folder.listFiles();
	    if(files!=null) { 
	        for(File f: files) {
	        	if(f.isDirectory())
	        		 delFldrCntnts(f);
	        	else if(f.isFile())
	        		f.delete();
	        }
	    }		   
	}
	
	public static List<String> getDirectories(String pth) {
		File [] files = new File(pth).listFiles();
		List<String> list = new ArrayList<String>();
		
		for(File f : files){
			if(f.isDirectory()){
				list.add(f.getAbsolutePath());
			}
		}
		
		return list;
	}
	
	/**
	 * @param queueLst
	 * @param pth
	 * @return
	 */
	public static Queue<File>  getFiles2(Queue<File> queueLst, String pth){
		File [] files = new File(pth).listFiles();
		
		for(File f : files){
			if(f.isDirectory()){
				return getFiles2(queueLst,f.getAbsolutePath());
			}else if(f.isFile()){
				queueLst.add(f);
			}
		}		
		return queueLst;
	}

	/**
	 * @param queueLst
	 * @param pth
	 * @return
	 */
	public static Queue<File>  getFiles(final Queue<File> queueLst, String pth){
		File [] files = new File(pth).listFiles();
		if(files != null)
			for(File f : files){		
				if(f.isFile()){
					queueLst.add(f);
				}
			}		
		return queueLst;
	}
	
	/**
	 * Static method that gets files within a directory if its extension
	 * @param queueLst
	 * @param pth
	 * @return
	 * @throws IOException 
	 */
	public static Queue<File>  getTargetFiles(final Queue<File> queueLst, String pth, String target,ImageFormat format) throws IOException{
		
		
		File [] files = new File(pth).listFiles();
		if(files != null)
			for(File f : files){		
//				System.out.println(f.getName()+" "+(f.isFile() && isTargetFile(f,target,format)) );
				if(f.isFile() && isTargetFile(f,target,format) ){
					queueLst.add(f);
				}else if(f.isFile()){
//					GlobalLookUp.fCnt++;
//					GlobalLookUp.logWriter.log(GlobalLookUp.logWriter.prepImgLog(Arrays.asList(f.getName(),GlobalLookUp.branchName,"Failed(Incompatible type)")), 
//							Level.INFO);
//					FileMngr.copyFile(f, GlobalLookUp.failedDir);
				}
			}		
		return queueLst;
	}
	
	/**
	 * Method that checks if file is a target file base on its extension
	 * @param file
	 * @param trgtExt
	 * @return
	 */
	public static boolean isTargetFile(File file,String trgtExt,ImageFormat format){		
		String ext = Files.getFileExtension(file.getName());
		if(null != format){
			if(Arrays.asList(format.getExtensions()).contains(ext))
				return true;
		}
		return false;
	}

}
