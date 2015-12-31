package com.fed.dev.utilities;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.StandardOpenOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.io.FilenameUtils;

public class FileUtility {
	/**
	 * Ensure slash at end of directory
	 * @param directory
	 * @return
	 */
	public String addSlashAtEndDir(String directory){
		String temp = directory;
		if(!(temp.endsWith(String.valueOf(File.separatorChar)) | temp.endsWith("/"))) {
			temp += File.separator;
		}
		return temp;
	}
	
	/**
	 * Replace illegal characters in directory
	 * @param directory
	 * @return
	 */
	public String replaceIllegalCharacters(String directory) {
		directory = directory.replaceAll("[#%&{}\\\\<>*?/$!'\":@]", "");
		return directory;
	}
	
	/**
	 * 
	 * @param directory
	 * @return TRUE if directory successfully created or is ALREADY exist, FALSE otherwise, check writing permission with target location
	 */
	public boolean ensureDirectoryExistence(String directory) {
		File dir = new File(directory);
		boolean dataToReturn = true;
		
		if(!dir.exists()) {
			dataToReturn = dir.mkdirs();
		}
		
		return dataToReturn;
	}

	public boolean deleteFile(String location) {
		File file = new File(location);
		return file.delete();
	}
	
	public byte[] readFile(File file) throws FileNotFoundException, IOException {
		byte[] blob = null;
		blob = Files.readAllBytes(file.toPath());
		return blob;
	}

	public void writeFile(byte[] blob, String path) throws FileNotFoundException, IOException {
		Files.write(Paths.get(path), blob, StandardOpenOption.CREATE);
	}
	
	public Object readObjectFromFilesystem(String location) throws FileNotFoundException, IOException, ClassNotFoundException {
		try(InputStream file = new FileInputStream(location);
			InputStream buffer = new BufferedInputStream(file);
			ObjectInput input = new ObjectInputStream(buffer);) {
			Object temp = input.readObject();
			return temp;
		}
	}
	
	public  void writeObjectToFilesystem(Object object, String location) throws FileNotFoundException, IOException {
		try ( OutputStream file = new FileOutputStream(location);
			  OutputStream buffer = new BufferedOutputStream(file);
		      ObjectOutput output = new ObjectOutputStream(buffer);) {
			output.writeObject(object);
		}
	}
	
	/**
	 * Get all files having the ext
	 * @param rootDirectory
	 * @param fileExtension
	 * @return
	 */
	public List<File> getFilesFromTreeDirByExtension(File rootDirectory, final String fileExtension) {
		final List<File> dataToReturn = new ArrayList<>();
		class InputFilesWalker extends SimpleFileVisitor<Path> {
			@Override
			public FileVisitResult preVisitDirectory(Path directory,
					BasicFileAttributes arg1) throws IOException {
				return FileVisitResult.CONTINUE;
			}
			@Override
			public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
				String extension = FilenameUtils.getExtension(file.getFileName().toString());
				if(extension.equalsIgnoreCase(fileExtension)) {
					dataToReturn.add(file.toFile());
				}
	            return FileVisitResult.CONTINUE;
	        }
	    }
	 
		InputFilesWalker walker = new InputFilesWalker();
	 
		try {
			Files.walkFileTree(rootDirectory.toPath(), walker);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return dataToReturn;
	}
	
	/**
	 * Get any files in file tree
	 * @param rootDirectory
	 * @return
	 */
	public List<File> getFilesFromTreeDirByExtension(File rootDirectory) {
		final List<File> dataToReturn = new ArrayList<>();
		class InputFilesWalker extends SimpleFileVisitor<Path> {
			@Override
			public FileVisitResult preVisitDirectory(Path directory,
					BasicFileAttributes arg1) throws IOException {
				return FileVisitResult.CONTINUE;
			}
			@Override
			public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
//				String extension = FilenameUtils.getExtension(file.getFileName().toString());
				dataToReturn.add(file.toFile());
	            return FileVisitResult.CONTINUE;
	        }
	    }
	 
		InputFilesWalker walker = new InputFilesWalker();
	 
		try {
			Files.walkFileTree(rootDirectory.toPath(), walker);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return dataToReturn;
	}
	
	/**
	 * Get any files in file tree
	 * @param rootDirectory
	 * @return
	 */
	public List<File> getFilesFromTreeDirByExtension(String rootDirectory) {
		File rootDir = new File(rootDirectory);
		final List<File> dataToReturn = new ArrayList<>();
		class InputFilesWalker extends SimpleFileVisitor<Path> {
			@Override
			public FileVisitResult preVisitDirectory(Path directory,
					BasicFileAttributes arg1) throws IOException {
				return FileVisitResult.CONTINUE;
			}
			@Override
			public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
				dataToReturn.add(file.toFile());
	            return FileVisitResult.CONTINUE;
	        }
	    }
	 
		InputFilesWalker walker = new InputFilesWalker();
	 
		try {
			Files.walkFileTree(rootDir.toPath(), walker);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return dataToReturn;
	}
	

	public List<Set<Path>> getFilesFromTreeDirByExtension(File rootDirectory, final String folderName, final String... fileExtension) {
		final List<Set<Path>> dataToReturn = new ArrayList<>();
		class InputFilesWalker extends SimpleFileVisitor<Path> {
			private Set<String> extensionSet = new HashSet<>();
			private boolean isAdd;
			private Set<Path> files;
			public InputFilesWalker() {
				for(String eachExt: fileExtension) {
					String tmp = eachExt.trim().toUpperCase();
					extensionSet.add(tmp);
				}
			}
			
			@Override
			public FileVisitResult preVisitDirectory(Path directory,
					BasicFileAttributes arg1) throws IOException {
				String dirName = directory.getFileName().toString();
				if(dirName.equalsIgnoreCase(folderName)) {
					files = new HashSet<>();
					dataToReturn.add(files);
					isAdd = true;
				} else {
					isAdd = false;
				}
				return FileVisitResult.CONTINUE;
			}
			@Override
			public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
				String extension = FilenameUtils.getExtension(file.getFileName().toString()).trim().toUpperCase();
				if(isAdd) {
					if(extensionSet.contains(extension)) {
						files.add(file);
					}
				}
				
	            return FileVisitResult.CONTINUE;
	        }
	    }
	 
		InputFilesWalker walker = new InputFilesWalker();
	 
		try {
			Files.walkFileTree(rootDirectory.toPath(), walker);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return dataToReturn;
	}
	
	public String getDateAsFileName() {
		//String fileName = new SimpleDateFormat("MMM'-'dd'-'yyyy'-'hh'_'mm'_'a'.log'").format(new Date());
		String fileName = new SimpleDateFormat("MMM'-'dd'-'yyyy").format(new Date());
		return fileName;
	}
	
	public String getDateAsFileName(String extension) {
		//String fileName = new SimpleDateFormat("MMM'-'dd'-'yyyy'-'hh'_'mm'_'a'.log'").format(new Date());
		String fileName = new SimpleDateFormat("MMM'-'dd'-'yyyy'."+extension+"'").format(new Date());
		return fileName;
	}
	
	public String getTimestampAsFileName() {
		String fileName = new SimpleDateFormat("MMM'-'dd'-'yyyy'-'hh'_'mm'_'a").format(new Date());
		return fileName;
	}
	
	public String getTimestampAsFileName(String extension) {
		String fileName = new SimpleDateFormat("MMM'-'dd'-'yyyy'-'hh'_'mm'_'a'."+extension+"'").format(new Date());
		return fileName;
	}
}
