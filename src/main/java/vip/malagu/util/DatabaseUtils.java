package vip.malagu.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
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
		PrintWriter printWriter = null;
		BufferedReader bufferedReader = null;
		try {
			Runtime runtime = Runtime.getRuntime();
			URL url = new URL("file:" + mysqlPath);
			String path = url.getPath();
			String cmd = "mysqldump -h" + hostIP + " -u" + userName + " -P" + hostPort + " -p" + password + " "
					+ databaseName;
			cmd = path + File.separator + cmd;
			Process process = runtime.exec(cmd);
			InputStreamReader inputStreamReader = new InputStreamReader(process.getInputStream(), "utf8");
			bufferedReader = new BufferedReader(inputStreamReader);
			printWriter = new PrintWriter(new OutputStreamWriter(new FileOutputStream(savePath + fileName), "utf8"));
			String line;
			while ((line = bufferedReader.readLine()) != null) {
				printWriter.println(line);
			}
			printWriter.flush();
			result.put("path", savePath + fileName);
			result.put("fileName", fileName);
		} catch (IOException e) {
			throw new Exception("连接错误、请联系管理员. " + e.getMessage());
		} finally {
			try {
				if (bufferedReader != null) {
					bufferedReader.close();
				}
				if (printWriter != null) {
					printWriter.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
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
			throw new Exception("数据库连接失败、请检查配置是否填写错误或联系管理员.");
		}
		return "数据库连接成功.";
	}
	

}
