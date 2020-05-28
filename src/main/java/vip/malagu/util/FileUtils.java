package vip.malagu.util;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.util.StringUtils;

import com.bstek.bdf3.dorado.jpa.JpaUtil;
import com.bstek.dorado.core.Configure;
import com.bstek.dorado.web.DoradoContext;

import vip.malagu.orm.FileInfo;

/**
 * 文件相关工具类
 * @author Lynn -- 2020年5月21日 下午5:12:51
 */
public final class FileUtils {

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
		File dir = new File(path);			
		return dir;

		
	}
	
	public static File getFileDirectory(){
		String prefix = Configure.getString("file.prefixPath");
		String suffix = new SimpleDateFormat("yyyy/MM/dd").format(new Date());
		File dir;
		String realPath;
		if (StringUtils.isEmpty(prefix)) {
			//realPath = DoradoContext.getAttachedServletContext().getRealPath("/");
			realPath = "src" + File.separator + "main" + File.separator + "resources";
			//realPath += File.separator + "WEB-INF" + File.separator + "files" + File.separator +suffix;
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
		if (file.exists()) {
			return true;
		}
		return false;
	}
	
	@Transactional
	public static void deleteModuleFile(String businessId, String module) {
		List<FileInfo> files = JpaUtil
			.linq(FileInfo.class)
			.equal("module", module)
			.equal("businessId", businessId)
			.list();
		if (!files.isEmpty()) {
			for (FileInfo f : files) {
				File file = new File(f.getPath());
				if (file.exists()) {
					file.delete();
				}
			}
			JpaUtil
				.lind(FileInfo.class)
				.equal("module", module)
				.equal("businessId", businessId)
				.delete();
		}
	}
	
	@Transactional
	public static void deleteFile(String fileId) {
		List<FileInfo> files = JpaUtil
			.linq(FileInfo.class)
			.equal("id", fileId)
			.list();
		if (!files.isEmpty()) {
			FileInfo fileInfo = files.get(0);
			File file = new File(fileInfo.getPath());
			if (file.exists()) {
				file.delete();
			}
			JpaUtil
				.lind(FileInfo.class)
				.equal("id", fileInfo.getId())
				.delete();
		}
	}
	
	@Transactional
	public static void deleteFile(FileInfo fileInfo) {
		File file = new File(fileInfo.getPath());
		if (file.exists()) {
			file.delete();
		}
		JpaUtil
			.lind(FileInfo.class)
			.equal("id", fileInfo.getId())
			.delete();
	}
	
}