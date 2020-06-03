package vip.malagu.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.util.StringUtils;

import com.bstek.bdf3.dorado.jpa.JpaUtil;
import com.bstek.dorado.core.Configure;
import com.bstek.dorado.web.DoradoContext;

import vip.malagu.custom.exception.CustomException;
import vip.malagu.enums.SystemErrorEnum;
import vip.malagu.orm.FileInfo;

/**
 * 文件相关工具类
 * @author Lynn -- 2020年5月21日 下午5:12:51
 */
public final class FileUtils {
	
	private FileUtils() {}

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
	
	public static boolean exist(String path) {
		File file = new File(path);
		return file.exists();
	}
	
	@Transactional
	public static void deleteModuleFileInfo(String businessId, String module) {
		List<FileInfo> files = JpaUtil
			.linq(FileInfo.class)
			.equal("module", module)
			.addIf(businessId)
				.equal("businessId", businessId)
			.endIf()
			.list();
		if (!files.isEmpty()) {
			for (FileInfo f : files) {
				try {
					Files.deleteIfExists(Paths.get(f.getPath()));
				} catch (IOException e) {
					throw new CustomException(SystemErrorEnum.FILE_DELETE_FAIL);
				}
			}
			JpaUtil
				.lind(FileInfo.class)
				.equal("module", module)
				.addIf(businessId)
					.equal("businessId", businessId)
				.endIf()
				.delete();
		}
	}
	
	@Transactional
	public static void deleteFileInfo(String fileId) {
		List<FileInfo> files = JpaUtil
			.linq(FileInfo.class)
			.equal("id", fileId)
			.list();
		if (!files.isEmpty()) {
			try {
				Files.deleteIfExists(Paths.get(files.get(0).getPath()));
			} catch (IOException e) {
				throw new CustomException(SystemErrorEnum.FILE_DELETE_FAIL);
			}
			JpaUtil
				.lind(FileInfo.class)
				.equal("id", fileId)
				.delete();
		}
	}
	
	@Transactional
	public static void deleteFileInfo(FileInfo fileInfo) {
		try {
			Files.deleteIfExists(Paths.get(fileInfo.getPath()));
		} catch (IOException e) {
			throw new CustomException(SystemErrorEnum.FILE_DELETE_FAIL);
		}
		JpaUtil
			.lind(FileInfo.class)
			.equal("id", fileInfo.getId())
			.delete();
	}
	
	public static void deleteFile(String path) {
		try {
			Files.deleteIfExists(Paths.get(path));
		} catch (IOException e) {
			throw new CustomException(SystemErrorEnum.FILE_DELETE_FAIL);
		}
	}
	
}