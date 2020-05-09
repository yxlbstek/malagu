package vip.malagu.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.bstek.dorado.annotation.DataProvider;
import com.bstek.dorado.annotation.DataResolver;
import com.bstek.dorado.annotation.Expose;
import com.bstek.dorado.data.provider.Page;

import vip.malagu.orm.FileInfo;
import vip.malagu.service.FileInfoService;
import vip.malagu.util.FileUtils;

@Controller
public class FileInfoController {

	@Autowired
	private FileInfoService fileInfoService;

	@DataProvider
	public void load(Page<FileInfo> page, Map<String, Object> param) {
		fileInfoService.load(page, param);
	}

	@DataResolver
	public void save(List<FileInfo> fileInfos) {
		fileInfoService.save(fileInfos);
	}

	@Expose
	public boolean exist(String path) {
		return FileUtils.exist(path);
	}

}
