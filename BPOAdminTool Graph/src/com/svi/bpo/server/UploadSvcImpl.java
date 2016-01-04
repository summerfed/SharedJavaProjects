package com.svi.bpo.server;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.svi.bpo.tools.DirectoryMngr;

@SuppressWarnings("serial")
public class UploadSvcImpl extends HttpServlet {

	public static final String DATE_FORMAT_NOW = "yyyy-MM-dd-HH-mm-ss";
	
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		boolean isMultipart = ServletFileUpload.isMultipartContent(req);
		
		if (isMultipart) {
			FileItemFactory factory = new DiskFileItemFactory();
			ServletFileUpload upload = new ServletFileUpload(factory);

			try {
				List<FileItem> items = upload.parseRequest(req);
				Iterator<FileItem> iterator = items.iterator();
				String upldDest = this.getServletContext().getRealPath("/temp/");
				FileItem item;
				
				while (iterator.hasNext()) {
					
					item = (FileItem) iterator.next();
					
					if (!item.isFormField()) {
						
						// Get upload destination 
					//	String[] upldTemp = upldDest.split("\\\\");
					//	String upldTyp = upldTemp[upldTemp.length-1];
						
						//Get filename
						String[] filePathToken = item.getName().split("\\\\");
				        String fileNameComp = filePathToken[filePathToken.length-1];
				        
				    /*    int i;
				        String fileName = new String();
						String[] a = fileNameComp.split("\\.");
						for (i = 0; i < a.length-2; i++){
							fileName += a[i] + ".";
						}
						fileName += a[i];
					*/	
						uploadFile(item, upldDest, fileNameComp);	
						
					}
				}
			} catch (FileUploadException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	private void uploadFile(FileItem item, String path, String filename) throws Exception {
		InputStream uploadedStream = item.getInputStream();
		
		DirectoryMngr.mkdir(path);
		
		File uploadedFile = new File(path , filename);
		
		FileOutputStream fos = new FileOutputStream(uploadedFile);
		StreamUtil.copyStream(uploadedStream, fos, true, true);
		item.write(uploadedFile);
		
		uploadedStream.close();
		fos.close();
	}
}
