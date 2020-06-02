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

public class AliyunOSSFileUtils {

	private static final String DOWNLOAD_FILE_PATH = "C:/tmp/";
	
	private static final String accessKeyId = "XXX";
	
	private static final String accessKeySecret = "XXX";
	
	private static final String endpoint = "http://oss-cn-beijing.aliyuncs.com";
	
	private static final String bucketName = "XXX";
	
	//private static final String bucketUrl = "http://XXX.oss-cn-beijing.aliyuncs.com";

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
		OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
		InputStream inputStream = multipartFile.getInputStream();
		ossClient.putObject(bucketName, fileName, inputStream);
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
	public static String uploadFile(InputStream inputStream, String userId, String path) throws IOException {
		OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
		ossClient.putObject(bucketName, path, inputStream);
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
	public static String uploadFile(File file, String userId, String path) throws IOException {
		OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
		ossClient.putObject(bucketName, path, file);
		ossClient.shutdown();
		return path;
	}

	/**
	 * 文件下载
	 * @param fileName 文件名称
	 * @param urlPrefix 路径前缀
	 * @return
	 * @throws Exception 
	 */
	public static boolean downloadFile(String fileName, String urlPrefix) throws Exception {
		OutputStream os = null;
		InputStream is = null;
		OSSClient ossClient = null;
		try {
			ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
			OSSObject ossObject = ossClient.getObject(bucketName, urlPrefix + fileName);
			is = ossObject.getObjectContent();
			byte[] bs = new byte[4096];
			int len;
			os = new FileOutputStream(DOWNLOAD_FILE_PATH + fileName);
			while ((len = is.read(bs)) != -1) {
				os.write(bs, 0, len);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			if (os != null) {
				os.close();
			}
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
		OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
		boolean result = ossClient.doesObjectExist(bucketName, fileName);
		ossClient.shutdown();
		return result;
	}

	/**
	 * 删除文件
	 * @param fileName 文件路径
	 */
	public static void deleteFile(String fileName) {
		OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
		ossClient.deleteObject(bucketName, fileName);
		ossClient.shutdown();
	}
	
}
