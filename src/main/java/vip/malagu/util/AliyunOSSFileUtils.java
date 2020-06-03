package vip.malagu.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;

import org.springframework.web.multipart.MultipartFile;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.OSSObject;

import vip.malagu.custom.exception.CustomException;
import vip.malagu.enums.SystemErrorEnum;

public class AliyunOSSFileUtils {
	
	/**
	 * private static final String bucketUrl = "http://XXX.oss-cn-beijing.aliyuncs.com";
	 */
	private AliyunOSSFileUtils() {}

	private static final String ACCESS_KEY_ID = "XXX";
	
	private static final String ACCESS_KEY_SECRET = "XXX";
	
	private static final String ENDPOINT = "http://oss-cn-beijing.aliyuncs.com";
	
	private static final String BUCKET_NAME = "XXX";
	
	/**
	 * 文件上传
	 * @param multipartFile 文件
	 * @param userId 用户ID
	 * @param urlPrefix 文件路径前缀
	 * @return
	 * @throws IOException
	 */
	public static String uploadFile(MultipartFile multipartFile, String userId, String urlPrefix) throws IOException {
		String fileName = urlPrefix + userId + "-" + new Date().getTime() + "-" + multipartFile.getOriginalFilename();
		OSSClient ossClient = new OSSClient(ENDPOINT, ACCESS_KEY_ID, ACCESS_KEY_SECRET);
		InputStream inputStream = multipartFile.getInputStream();
		ossClient.putObject(BUCKET_NAME, fileName, inputStream);
		ossClient.shutdown();
		return fileName;
	}

	/**
	 * 文件上传
	 * @param inputStream 文件流
	 * @param userId 用户ID
	 * @param path 文件路径
	 * @return
	 * @throws IOException
	 */
	public static String uploadFile(InputStream inputStream, String path) throws IOException {
		OSSClient ossClient = new OSSClient(ENDPOINT, ACCESS_KEY_ID, ACCESS_KEY_SECRET);
		ossClient.putObject(BUCKET_NAME, path, inputStream);
		ossClient.shutdown();
		return path;
	}

	/**
	 * 文件上传
	 * @param file 文件
	 * @param userId 用户ID
	 * @param path 文件路径
	 * @return
	 * @throws IOException
	 */
	public static String uploadFile(File file, String path) throws IOException {
		OSSClient ossClient = new OSSClient(ENDPOINT, ACCESS_KEY_ID, ACCESS_KEY_SECRET);
		ossClient.putObject(BUCKET_NAME, path, file);
		ossClient.shutdown();
		return path;
	}

	/**
	 * 文件下载
	 * @param fileName 文件名称
	 * @param urlPrefix 路径前缀
	 * @param downloadPath 文件下载路径
	 * @return
	 * @throws IOException 
	 */
	public static boolean downloadFile(String fileName, String urlPrefix, String downloadPath) throws IOException {
		InputStream is = null;
		OSSClient ossClient = null;
		try {
			ossClient = new OSSClient(ENDPOINT, ACCESS_KEY_ID, ACCESS_KEY_SECRET);
			OSSObject ossObject = ossClient.getObject(BUCKET_NAME, urlPrefix + fileName);
			is = ossObject.getObjectContent();
			byte[] bs = new byte[4096];
			int len;
			
			try (OutputStream os = new FileOutputStream(downloadPath + fileName)) {
				while ((len = is.read(bs)) != -1) {
					os.write(bs, 0, len);
				}
			}
		} catch (Exception e) {
			throw new CustomException("文件下载失败", SystemErrorEnum.FAIL.getStatus());
		} finally {
			if (is != null) {
				is.close();
			}
			if (ossClient != null) {
				ossClient.shutdown();
			}
		}
		return true;
	}

	/**
	 * 判断阿里云是否有该文件
	 * @param fileName 文件路径
	 * @return
	 */
	public static boolean exist(String fileName) {
		OSSClient ossClient = new OSSClient(ENDPOINT, ACCESS_KEY_ID, ACCESS_KEY_SECRET);
		boolean result = ossClient.doesObjectExist(BUCKET_NAME, fileName);
		ossClient.shutdown();
		return result;
	}

	/**
	 * 删除文件
	 * @param fileName 文件路径
	 */
	public static void deleteFile(String fileName) {
		OSSClient ossClient = new OSSClient(ENDPOINT, ACCESS_KEY_ID, ACCESS_KEY_SECRET);
		ossClient.deleteObject(BUCKET_NAME, fileName);
		ossClient.shutdown();
	}
	
}
