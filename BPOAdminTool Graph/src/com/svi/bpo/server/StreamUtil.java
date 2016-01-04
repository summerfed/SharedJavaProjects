package com.svi.bpo.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


public class StreamUtil {
	
	/**
	 * Copies the contents of the inputstream to the outputstream.
	 * @param inputStream the inputstream to read
	 * @param outputStream the outputstream to write to
	 * @param closeInput true to close the inputstream on completion
	 * @param closeOutput true to close the outputstream on completion
	 * @throws SimXcpt if an error occurs
	 */
	public static void copyStream(InputStream inputStream,
			OutputStream outputStream, boolean closeInput,
			boolean closeOutput) throws Exception {

		try {
			byte[] buffer = new byte[512];
			int read = 0;
			while ((read = inputStream.read(buffer)) != -1) {
				outputStream.write(buffer, 0, read);
			}
		} catch (IOException e) {
			throw new Exception(e);
		} finally {
			if (closeInput) {
				closeStream(inputStream);
			}
			if (closeOutput) {
				closeStream(outputStream);
			}
		}
	}

	/**
	 * Close an InputStream.
	 *
	 * @param stream the stream to close
	 * @throws SimXcpt 
	 */
	public static void closeStream(InputStream stream) throws Exception {
		if (stream != null) {
			try {
				stream.close();
				
			} catch (IOException e) {
				throw new Exception(e);
			}
		}
	}

	/**
	 * Close an OutputStream.
	 *
	 * @param stream the stream to close
	 * @throws SimXcpt 
	 */
	public static void closeStream(OutputStream stream) throws Exception {
		if (stream != null) {
			try {
				stream.close();
			} catch (IOException e) {
				throw new Exception(e);
			}
		}
	}
}
