package vip.malagu.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.bstek.dorado.core.Configure;

import vip.malagu.custom.exception.CustomException;
import vip.malagu.enums.SystemErrorEnum;

/**
 * 数据库相关工具类
 * @author Lynn -- 2020年5月21日 下午5:11:12
 */
public final class DatabaseUtils {

	/**
	 * Mysql数据库备份
	 * 
	 * @param hostIP 数据库地址
	 * @param hostPort 端口
	 * @param userName 用户名
	 * @param password 密码
	 * @param databaseName 要导出数据库名
	 * @param mysqlPath 数据库安装bin路径
	 * @return
	 * @throws Exception 
	 */
	public static Map<String, String> exportDatabase(String hostIP, String hostPort, String userName, String password, String databaseName, String mysqlPath) throws Exception {
		Map<String, String> result = new HashMap<>();
		String prefix = Configure.getString("file.prefixPath");
		String savePath = prefix + File.separator + "backupsDb" + File.separator;
		String fileName = "数据库备份" + DateUtils.dateToString(new Date(),  "yyyyMMddHHmmss") + ".sql";
		File saveFile = new File(savePath);
		if (!saveFile.exists()) {
			saveFile.mkdirs();
		}
		Runtime runtime = Runtime.getRuntime();
		URL url = new URL("file:" + mysqlPath);
		String path = url.getPath();
		String cmd = "mysqldump -h" + hostIP + " -u" + userName + " -P" + hostPort + " -p" + password + " "
				+ databaseName;
		cmd = path + File.separator + cmd;
		Process process = runtime.exec(cmd);
		try (InputStreamReader inputStreamReader = new InputStreamReader(process.getInputStream(), "utf8")) {
			try (BufferedReader bufferedReader = new BufferedReader(inputStreamReader)) {
				try (PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(new FileOutputStream(savePath + fileName), "utf8"))) {
					String line;
					while ((line = bufferedReader.readLine()) != null) {
						printWriter.println(line);
					}
					printWriter.flush();
					result.put("path", savePath + fileName);
					result.put("fileName", fileName);
				}
			}
		} catch (Exception e) {
			throw new CustomException(SystemErrorEnum.DATABASE_CONNECTION_ERROR);
		}
		return result;
	}
	
	/**
	 * 数据库连接
	 * 
	 * @param driver
	 * @param url
	 * @param username
	 * @param password
	 * @return
	 * @throws Exception
	 */
	public static String connectionDb(String driver, String url, String username, String password) throws Exception {
		try {
			Class.forName(driver);
			Connection conn = DriverManager.getConnection(url, username, password);
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
			throw new CustomException(SystemErrorEnum.DATABASE_CONNECTION_ERROR_TIP);
		}
		return "数据库连接成功";
	}
	

}
