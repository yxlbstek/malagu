package vip.malagu.service.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bstek.bdf3.dorado.jpa.JpaUtil;
import com.bstek.bdf3.dorado.jpa.policy.SaveContext;
import com.bstek.bdf3.dorado.jpa.policy.impl.SmartSavePolicyAdapter;
import com.bstek.dorado.data.provider.Page;

import vip.malagu.constants.PropertyConstant;
import vip.malagu.custom.exception.CustomException;
import vip.malagu.enums.SystemErrorEnum;
import vip.malagu.orm.FileInfo;
import vip.malagu.service.FileInfoService;
import vip.malagu.util.DateUtils;

@Service
public class FileInfoServiceImpl implements FileInfoService {
	
	@Override
	@Transactional(readOnly = true)
	public void load(Page<FileInfo> page, Map<String, Object> param) {
		String fileName = (String) param.get("fileName"); 
		String creator = (String) param.get("creator");
		Date createDate = (Date) param.get(PropertyConstant.CREATE_DATE);
		Date startDate = null;
		Date endDate = null;
		if (createDate != null) {
			startDate = DateUtils.startTime(createDate);
			endDate = DateUtils.endTime(createDate);
		}
		JpaUtil
			.linq(FileInfo.class)
			.equal("module", "FileInfoMaintain")
			.addIf(fileName)
				.like("fileName", "%" + fileName + "%")
			.endIf()
			.addIf(creator)
				.or()
					.like("creator", "%" + creator + "%")
					.equal("username", creator)
				.end()
			.endIf()
			.addIf(startDate != null && endDate != null)
				.between(PropertyConstant.CREATE_DATE, startDate, endDate)
			.endIf()
			.desc(PropertyConstant.CREATE_DATE)
			.paging(page);
	}

	@Override
	@Transactional
	public void save(List<FileInfo> fileInfos) {
		JpaUtil.save(fileInfos, new SmartSavePolicyAdapter() {

			@Override
			public boolean beforeDelete(SaveContext context) {
				FileInfo fileInfo = context.getEntity();
				try {
					Files.deleteIfExists(Paths.get(fileInfo.getPath()));
				} catch (IOException e) {
					throw new CustomException(SystemErrorEnum.FILE_DELETE_FAIL);
				}
				int index = fileInfo.getPath().lastIndexOf('.');
				String previewFilePath = fileInfo.getPath().substring(0, index) + ".pdf";
				try {
					Files.deleteIfExists(Paths.get(previewFilePath));
				} catch (IOException e) {
					throw new CustomException(SystemErrorEnum.FILE_DELETE_FAIL);
				}
				return true;
			}

		});
	}
	
}
