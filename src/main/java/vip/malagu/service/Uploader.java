package vip.malagu.service;

import java.io.File;
import java.io.FileInputStream;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

import org.jodconverter.DocumentConverter;
import org.malagu.linq.JpaUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.bstek.bdf3.security.ContextUtils;
import com.bstek.dorado.uploader.DownloadFile;
import com.bstek.dorado.uploader.UploadFile;
import com.bstek.dorado.uploader.annotation.FileProvider;
import com.bstek.dorado.uploader.annotation.FileResolver;

import vip.malagu.custom.exception.CustomException;
import vip.malagu.enums.SystemErrorEnum;
import vip.malagu.orm.FileInfo;
import vip.malagu.util.DateUtils;
import vip.malagu.util.FileUtils;

@Component("uploader")
public class Uploader {
	
	@Autowired
    private DocumentConverter converter;

	@FileResolver
	@Transactional
	public FileInfo upload(UploadFile file, Map<String, Object> param) throws Exception {
		FileInfo fileInfo = new FileInfo();
		fileInfo.setId(UUID.randomUUID().toString());
		fileInfo.setCatagoryId((String) param.get("catagoryId"));
		fileInfo.setBusinessId((String) param.get("businessId"));
		fileInfo.setModule((String) param.get("module"));
		fileInfo.setPosition((String) param.get("position"));
		fileInfo.setFileName(file.getFileName());
		fileInfo.setType((Integer) param.get("type"));
		fileInfo.setSize(file.getSize());
		fileInfo.setCreateDate(new Date());
		fileInfo.setCreator(ContextUtils.getLoginUser().getNickname());
		fileInfo.setUsername(ContextUtils.getLoginUsername());
		fileInfo.setRemark((String) param.get("remark"));
		String fileName = file.getFileName();
		String fileSuf = getFileSuf(fileName);
		File dest = new File(FileUtils.getFileDirectory(), ContextUtils.getLoginUsername() + "@" + DateUtils.dateToString(new Date(), "yyyyMMddHHmmss") + "@" + fileInfo.getId() + fileSuf);
		fileInfo.setPath(dest.getAbsolutePath());
		JpaUtil.persist(fileInfo);
		org.apache.commons.io.FileUtils.copyInputStreamToFile(file.getInputStream(), dest);
		//生成在线预览pdf文件
		int nameIndex = fileName.lastIndexOf(".");
		String suf = fileName.substring(nameIndex + 1, fileName.length());
		if (suf.equals("doc") 
				|| suf.equals("docx") || suf.equals("xls")
				|| suf.equals("xlsx") || suf.equals("ppt")) {
			File existFile = new File(fileInfo.getPath());
			int index = fileInfo.getPath().lastIndexOf(".");
			String newFilePath = fileInfo.getPath().substring(0, index) + ".pdf";
			try {
			    converter.convert(existFile).to(new File(newFilePath)).execute();
			} catch (Exception e) {
				throw new CustomException(SystemErrorEnum.FAIL);
			}
		}
		return fileInfo;
	}
	
	private String getFileSuf(String fileName) {
		int index = fileName.lastIndexOf(".");
		return fileName.substring(index, fileName.length());
	}

	@FileProvider
	@Transactional(readOnly = true)
	public DownloadFile download(Map<String, String> parameter) throws Exception {
		String path = parameter.get("path");
		String fileName = parameter.get("fileName");
		return new DownloadFile(fileName, new FileInputStream(path));
	}

}
