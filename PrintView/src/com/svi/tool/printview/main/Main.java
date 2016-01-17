package com.svi.tool.printview.main;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.swing.JFileChooser;

import org.apache.commons.io.FilenameUtils;

import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfWriter;
import com.svi.printer.SilentPrinter;

public class Main {
	  public static void main(String s[]) {
	    JFileChooser chooser = new JFileChooser();
	    chooser.setCurrentDirectory(new java.io.File("."));
	    chooser.setDialogTitle("Select Folder To Print");
	    chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
	    chooser.setAcceptAllFileFilterUsed(false);
	    if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
//	      System.out.println("getCurrentDirectory(): " + chooser.getCurrentDirectory());
//	      System.out.println("getSelectedFile() : " + chooser.getSelectedFile());
	      File tempDir = new File("tmp");
	      if(!tempDir.exists()) {
	    	  tempDir.mkdir();
	      }
	      
	      File printDir = chooser.getSelectedFile();
	      for (File eachFile : printDir.listFiles()) {
			String ext = FilenameUtils.getExtension(eachFile.getName());
			if(ext.equalsIgnoreCase("PDF")) {
				print(eachFile);
			} else if(ext.equalsIgnoreCase("PNG")) {
				File file = convertToPdf(eachFile);
				print(file);
				file.delete();
			} else if(ext.equalsIgnoreCase("JPG")) {
				File file = convertToPdf(eachFile);
				print(file);
				file.delete();
			} else if(ext.equalsIgnoreCase("JPEG")) {
				File file = convertToPdf(eachFile);
				print(file);
				file.delete();
			}
			
	      }
	    } else {
	      System.out.println("No Selection ");
	    }
	  }
	  
	  private static boolean print(File fileToPrint) {
		  boolean isPrinted = SilentPrinter.printFile(fileToPrint, "", 1);
		  return isPrinted;
	  }
	  
	  private static File convertToPdf(File imageFile) {
		  Document document = new Document();
		    String output = "tmp"+File.separatorChar+imageFile.getName()+".pdf";
		    try {
		      FileOutputStream fos = new FileOutputStream(output);
		      PdfWriter writer = PdfWriter.getInstance(document, fos);
		      writer.open();
		      document.open();
		      
		      int indentation = 0;
		      Image img = Image.getInstance
		    	         (imageFile.getAbsolutePath());
		      float scaler = ((document.getPageSize().getWidth() - document.leftMargin()
		               - document.rightMargin() - indentation) / img.getWidth()) * 100;
		      img.scalePercent(scaler);
		      document.add(img);
		      document.close();
		      writer.close();
		    }
		    catch (Exception e) {
		      e.printStackTrace();
		    }
		 return new File(output);
	  }
}
