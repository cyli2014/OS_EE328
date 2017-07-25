//package homework4;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class WGet {
	public static void main(String[] args) {
		try {
			downloadFromUrl(args[0], args[1]);
		} catch (ExistingFileException f) {
			System.out.println(f.getMessage());
			f.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void downloadFromUrl(String urlStr, String fileName) throws IOException, ExistingFileException {
		URL url = new URL(urlStr);
		HttpURLConnection connect = (HttpURLConnection) url.openConnection();
		
		connect.setConnectTimeout(3 * 1000);
		connect.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");

		InputStream inputStream = connect.getInputStream();
		byte[] getData = readInputStream(inputStream);

		String savePath = "C:\\Users\\Darwin\\Desktop";
		File saveDir = new File(savePath);
		File file = new File(saveDir + File.separator + fileName);

		if (file.exists())
			throw new ExistingFileException("File " + fileName + " already exists.");
		else {
			FileOutputStream fos = new FileOutputStream(file);
			fos.write(getData);
			if (fos != null) {
				fos.close();
			}
			if (inputStream != null) {
				inputStream.close();
			}

			System.out.println(url + " download success.");
		}

	}

	public static byte[] readInputStream(InputStream inputStream) throws IOException {
		byte[] buffer = new byte[1024];
		int len = 0;
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		while ((len = inputStream.read(buffer)) != -1) {
			bos.write(buffer, 0, len);
		}
		bos.close();
		return bos.toByteArray();
	}
}

class ExistingFileException extends Exception {
	private static final long serialVersionUID = 1L;

	public ExistingFileException(String msg) {
		super(msg);
	}
}
