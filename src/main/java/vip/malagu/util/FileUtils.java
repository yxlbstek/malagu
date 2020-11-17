package vip.malagu.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.Java2DFrameConverter;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.GetObjectRequest;
import com.aliyun.oss.model.OSSObject;
import com.bstek.dorado.core.Configure;
import com.bstek.dorado.web.DoradoContext;

import net.coobird.thumbnailator.Thumbnails;
import vip.malagu.custom.exception.CustomException;
import vip.malagu.enums.SystemErrorEnum;

/**
 * 文件相关工具类
 * @author Lynn -- 2020年5月21日 下午5:12:51
 */
public final class FileUtils {
	
	/**
	 * private static final String bucketUrl = "http://XXX.oss-cn-beijing.aliyuncs.com";
	 */
	private FileUtils() {}

	private static final String ACCESS_KEY_ID = "XXX";
	
	private static final String ACCESS_KEY_SECRET = "XXX";
	
	private static final String ENDPOINT = "http://oss-cn-beijing.aliyuncs.com";
	
	private static final String BUCKET_NAME = "XXX";
	
	public static final String DOWNLOAD_FILE_PATH = "/usr/";

	public static final String OSS_URL_PREX = "img/";
	
	public static File getTempDirectory() {
		File tempDirectory = new File(System.getProperty("java.io.tmpdir"), "urr");
		if (!tempDirectory.exists() || !tempDirectory.isDirectory()) {
			tempDirectory.mkdirs();
		}
		return tempDirectory;
	}
	
	
	public static File getWebDirectory(String path){
		String realPath = DoradoContext.getAttachedServletContext().getRealPath("/");
		path = realPath + File.separator + path;		
		return new File(path);

		
	}
	
	/**
	 * realPath = DoradoContext.getAttachedServletContext().getRealPath("/");
	 * realPath += File.separator + "WEB-INF" + File.separator + "files" + File.separator +suffix;
	 * @return
	 */
	public static File getFileDirectory(){
		String prefix = Configure.getString("file.prefixPath");
		String suffix = new SimpleDateFormat("yyyy/MM/dd").format(new Date());
		File dir;
		String realPath;
		if (StringUtils.isEmpty(prefix)) {
			realPath = "src" + File.separator + "main" + File.separator + "resources";
			realPath += File.separator + "static" + File.separator + "files" + File.separator + suffix;
		} else {
			if (StringUtils.endsWithIgnoreCase(prefix, File.separator)) {
				realPath = prefix + suffix;
			} else {
				realPath = prefix + File.separator + suffix;
			}
		}
		dir = new File(realPath);
		if(!dir.exists() || !dir.isDirectory()){
			dir.mkdirs();
		}
		return dir;
	}
	
	/**
	 * 判断文件是否存在
	 * @param path
	 * @return
	 */
	public static boolean existLocal(String path) {
		File file = new File(path);
		return file.exists();
	}
	
	/**
	 * 删除文件
	 * @param path
	 */
	public static void deleteLocalFile(String path) {
		try {
			Files.deleteIfExists(Paths.get(path));
		} catch (IOException e) {
			throw new CustomException(SystemErrorEnum.FILE_DELETE_FAIL);
		}
	}
	
	/**
	 * 文件上传至OSS
	 * @param multipartFile 文件
	 * @param userId 用户ID
	 * @param urlPrefix 文件路径前缀
	 * @return
	 * @throws IOException
	 */
	public static String uploadFileToOss(MultipartFile multipartFile, String userId, String urlPrefix) throws IOException {
		int index = multipartFile.getOriginalFilename().lastIndexOf(".");
		String fileName = urlPrefix + userId + "-" + UUID.randomUUID().toString().replaceAll("-", "") + "-YT" + multipartFile.getOriginalFilename().substring(index, multipartFile.getOriginalFilename().length());
		OSSClient ossClient = new OSSClient(ENDPOINT, ACCESS_KEY_ID, ACCESS_KEY_SECRET);
		InputStream inputStream = multipartFile.getInputStream();
		ossClient.putObject(BUCKET_NAME, fileName, inputStream);
		ossClient.shutdown();
		return fileName;
	}

	/**
	 * 文件上传至OSS
	 * @param inputStream 文件流
	 * @param userId 用户ID
	 * @param path 文件路径
	 * @return
	 * @throws IOException
	 */
	public static String uploadFileToOss(InputStream inputStream, String path) throws IOException {
		OSSClient ossClient = new OSSClient(ENDPOINT, ACCESS_KEY_ID, ACCESS_KEY_SECRET);
		ossClient.putObject(BUCKET_NAME, path, inputStream);
		ossClient.shutdown();
		return path;
	}

	/**
	 * 文件上传至OSS
	 * @param file 文件
	 * @param userId 用户ID
	 * @param path 文件路径
	 * @return
	 * @throws IOException
	 */
	public static String uploadFileToOss(File file, String path) throws IOException {
		OSSClient ossClient = new OSSClient(ENDPOINT, ACCESS_KEY_ID, ACCESS_KEY_SECRET);
		ossClient.putObject(BUCKET_NAME, path, file);
		ossClient.shutdown();
		return path;
	}

	/**
	 * OSS文件下载(浏览器)
	 * @param fileName 文件名称
	 * @param urlPrefix 路径前缀
	 * @param downloadPath 文件下载路径
	 * @return
	 * @throws IOException 
	 */
	public static boolean downloadOssFile(String fileName, String urlPrefix, String downloadPath) throws IOException {
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
	 * OSS文件下载(文件夹)
	 * @param fileName
	 * @return 本路路径
	 */
	public static String downloadOssFileToLocalTmp(String fileName) {
		OSSClient ossClient = null;
		try {
			ossClient = new OSSClient(ENDPOINT, ACCESS_KEY_ID, ACCESS_KEY_SECRET);
			String[] arr = fileName.split("/");
			String path = DOWNLOAD_FILE_PATH + arr[arr.length - 1];
			ossClient.getObject(new GetObjectRequest(BUCKET_NAME, fileName), new File(path));
			return path;
		} catch (Exception e) {
			throw new CustomException("文件下载失败", SystemErrorEnum.FAIL.getStatus());
		} finally {
			if (ossClient != null) {
				ossClient.shutdown();
			}
		}
	}

	/**
	 * 判断OSS是否有该文件
	 * @param fileName 文件路径
	 * @return
	 */
	public static boolean existOss(String fileName) {
		OSSClient ossClient = new OSSClient(ENDPOINT, ACCESS_KEY_ID, ACCESS_KEY_SECRET);
		boolean result = ossClient.doesObjectExist(BUCKET_NAME, fileName);
		ossClient.shutdown();
		return result;
	}

	/**
	 * 删除OSS上文件
	 * @param fileName 文件路径
	 */
	public static void deleteOssFile(String fileName) {
		OSSClient ossClient = new OSSClient(ENDPOINT, ACCESS_KEY_ID, ACCESS_KEY_SECRET);
		ossClient.deleteObject(BUCKET_NAME, fileName);
		ossClient.shutdown();
	}
	
	/**
	 * 压缩IndexPicture, 两种格式图片
	 * @param pathSufx
	 * @return
	 */
	public void compressIndexImg(String pathSufx) {
		int index = pathSufx.lastIndexOf("/");
		String oldFileName = pathSufx.substring(index + 1, pathSufx.length());
		//官网使用
		String gwFileName = oldFileName.replace("YT", "WEB");
		String gwFilePath = DOWNLOAD_FILE_PATH + gwFileName;
		//app使用
		String appFileName = oldFileName.replace("YT", "APP");
		String appFilePath = DOWNLOAD_FILE_PATH + appFileName;
		
		boolean exist = existOss(OSS_URL_PREX + gwFileName);
		if (!exist) {
			String downloadPath = downloadOssFileToLocalTmp(pathSufx);
			try {
				File file = new File(downloadPath);
				if (file.length() > (600 * 1024)) {
					Thumbnails.of(downloadPath).scale(0.3).toFile(gwFilePath);
					Thumbnails.of(downloadPath).size(520, 800).keepAspectRatio(false).toFile(appFilePath);
					uploadFileToOss(new File(gwFilePath), OSS_URL_PREX + gwFileName);
					uploadFileToOss(new File(appFilePath), OSS_URL_PREX + appFileName);
				} else {
					uploadFileToOss(new File(downloadPath), OSS_URL_PREX + appFileName);
				}
			} catch (IOException e) {
				e.printStackTrace();
				AssertUtils.errorMsg("文件上传失败");
			} finally {
				deleteLocalFile(downloadPath);
				deleteLocalFile(gwFilePath);
				deleteLocalFile(appFilePath);
			}
		}
	}
	
	/**
	 * 压缩自定义后缀类型的图片
	 * @param pathSufx
	 * @param type 自定义后缀类型
	 * @param scale 压缩比例
	 * @return
	 */
	public String compressCustomImg(String pathSufx, String type, double scale) {
		int index = pathSufx.lastIndexOf("/");
		String oldFileName = pathSufx.substring(index + 1, pathSufx.length());
		String newFileName = oldFileName.replace("YT", type);
		String newFilePath = DOWNLOAD_FILE_PATH + newFileName;
		boolean exist = existOss(OSS_URL_PREX + newFileName);
		if (exist) {
			return OSS_URL_PREX + newFileName;
		}
		String downloadPath = downloadOssFileToLocalTmp(pathSufx);
		try {
			File file = new File(downloadPath);
			if (file.length() > (600 * 1024)) {
				Thumbnails.of(downloadPath).scale(scale).toFile(newFilePath);
				uploadFileToOss(new File(newFilePath), OSS_URL_PREX + newFileName);
				return OSS_URL_PREX + newFileName;
			}
			return pathSufx;
		} catch (IOException e) {
			e.printStackTrace();
			AssertUtils.errorMsg("文件上传失败");
		} finally {
			deleteLocalFile(downloadPath);
			deleteLocalFile(newFilePath);
		}
		return null;
	}
	
	/**
	 * 获取视频图片
	 * @param flashPathSufx
	 * @return
	 * @throws Exception
	 */
	public String buildFlashPicture(String flashPathSufx) {
		boolean exist = existOss(flashPathSufx);
		if (exist) {
			String localPath = downloadOssFileToLocalTmp(flashPathSufx);
			FFmpegFrameGrabber fFmpegFrameGrabber = null;
			String flashPicture = null;
			Frame frame = null;
			int flag = 0;
			try {
				//获取视频文件
				fFmpegFrameGrabber = FFmpegFrameGrabber.createDefault(localPath);
				fFmpegFrameGrabber.start();
				// 获取视频总帧数
				int ftp = fFmpegFrameGrabber.getLengthInFrames();
				String fileName = null;
				while (flag <= ftp) {
					frame = fFmpegFrameGrabber.grabImage();
					int height = (int) (((double) 340 / frame.imageWidth) * frame.imageHeight);
					fFmpegFrameGrabber.setImageHeight(height);
					fFmpegFrameGrabber.setImageWidth(340);
					if (frame != null && flag == 5) {
						fileName = UUID.randomUUID().toString().replaceAll("-", "") + ".png";
						flashPicture = File.separator + localPath.split("/")[1] + File.separator + fileName;
						File outPut = new File(flashPicture);
						ImageIO.write(FrameToBufferedImage(frame), "png", outPut);
						break;
					}
					flag++;
				}
				return uploadFileToOss(new File(flashPicture), OSS_URL_PREX + "Flash-" + fileName);
			} catch (Exception e) {
				e.printStackTrace();
				AssertUtils.errorMsg("文件上传失败");
			} finally {
				deleteLocalFile(localPath);
				deleteLocalFile(flashPicture);
				
				if (fFmpegFrameGrabber != null) {
					try {
						fFmpegFrameGrabber.stop();
						fFmpegFrameGrabber.close();
					} catch (org.bytedeco.javacv.FrameGrabber.Exception e) {
						AssertUtils.errorMsg("资源释放失败");
					}
				}
			}
		}
		return null;
	}

	public BufferedImage FrameToBufferedImage(Frame frame) {
		Java2DFrameConverter converter = new Java2DFrameConverter();
		BufferedImage bufferedImage = converter.getBufferedImage(frame);
		return bufferedImage;
	}
	
}