package vip.malagu.service;

import java.util.List;
import java.util.Map;

import com.bstek.dorado.data.provider.Page;

import vip.malagu.orm.FileInfo;

public interface FileInfoService {

	void load(Page<FileInfo> page, Map<String, Object> param);

	void save(List<FileInfo> fileInfos);

}