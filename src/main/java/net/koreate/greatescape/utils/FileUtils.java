package net.koreate.greatescape.utils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.UUID;
import javax.imageio.ImageIO;
import org.apache.commons.io.IOUtils;
import org.imgscalr.Scalr;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;

public class FileUtils {
	public static String uploadFile(String realPath, MultipartFile file) throws Exception {
		String uploadFileName = "";
		UUID uid = UUID.randomUUID();
		String originalName = file.getOriginalFilename();
		String savedName = uid.toString().replace("-", "");
		savedName = String.valueOf(savedName) + "_" + originalName.replace("_", " ");
		String datePath = calcPath(realPath);
		File f = new File(String.valueOf(realPath) + datePath, savedName);
		file.transferTo(f);
		String formatName = originalName.substring(originalName.lastIndexOf(".") + 1);
		if (MediaUtils.getMediaType(formatName) != null) {
			uploadFileName = makeThumbnail(realPath, datePath, savedName);
		} else {
			uploadFileName = makeFileName(realPath, datePath, savedName);
		}
		return uploadFileName;
	}

	private static String makeFileName(String realPath, String datePath, String savedName) {
		String fileName = "";
		fileName = String.valueOf(datePath) + File.separator + savedName;
		fileName = fileName.replace(File.separatorChar, '/');
		return fileName;
	}

	private static String makeThumbnail(String realPath, String datePath, String savedName) throws IOException {
		String name = "";
		File file = new File(String.valueOf(realPath) + datePath, savedName);
		BufferedImage image = ImageIO.read(file);
		BufferedImage sourceImage = Scalr.resize(image, Scalr.Method.AUTOMATIC, Scalr.Mode.FIT_TO_HEIGHT, 250,
				new java.awt.image.BufferedImageOp[0]);
		String thumbnailImage = String.valueOf(realPath) + datePath + File.separator + "s_" + savedName;
		String ext = savedName.substring(savedName.lastIndexOf(".") + 1);
		ImageIO.write(sourceImage, ext, new File(thumbnailImage));
		name = thumbnailImage.substring(realPath.length()).replace(File.separatorChar, '/');
		return name;
	}

	public static String calcPath(String realPath) {
		String datePath = "";
		Calendar cal = Calendar.getInstance();
		String yearPath = String.valueOf(File.separator) + cal.get(1);
		String monthPath = String.valueOf(yearPath) + File.separator
				+ (new DecimalFormat("00")).format((cal.get(2) + 1));
		datePath = String.valueOf(monthPath) + File.separator + (new DecimalFormat("00")).format(cal.get(5));
		mkDir(realPath, new String[] { yearPath, monthPath, datePath });
		return datePath;
	}

	public static void mkDir(String realPath, String... path) {
		if ((new File(String.valueOf(realPath) + path[path.length - 1])).exists())
			return;
		byte b;
		int i;
		String[] arrayOfString;
		for (i = (arrayOfString = path).length, b = 0; b < i;) {
			String p = arrayOfString[b];
			String mkDir = String.valueOf(realPath) + p;
			File file = new File(mkDir);
			if (!file.exists())
				file.mkdir();
			b++;
		}
	}

	public static boolean deleteFile(String realPath, String fileName) throws Exception {
		boolean isDeleted = false;
		String formatName = fileName.substring(fileName.lastIndexOf(".") + 1);
		fileName = fileName.replace('/', File.separatorChar);
		isDeleted = (new File(String.valueOf(realPath) + fileName)).delete();
		if (MediaUtils.getMediaType(formatName) != null) {
			fileName = fileName.replace("s_", "");
			isDeleted = (new File(String.valueOf(realPath) + fileName)).delete();
		}
		return isDeleted;
	}

	public static byte[] getBytes(String realPath, String fileName) throws Exception {
		File file = new File(realPath, fileName);
		InputStream is = new FileInputStream(file);
		return IOUtils.toByteArray(is);
	}

	public static HttpHeaders getOctetHeaders(String fileName) throws Exception {
		HttpHeaders header = new HttpHeaders();
		header.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		fileName = fileName.substring(fileName.lastIndexOf("_") + 1);
		header.add("content-disposition",
				"attachment;fileName=\"" + new String(fileName.getBytes("UTF-8"), "ISO-8859-1") + "\"");
		return header;
	}
}
