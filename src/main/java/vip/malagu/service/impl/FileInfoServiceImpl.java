package vip.malagu.service.impl;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bstek.bdf3.dorado.jpa.JpaUtil;
import com.bstek.bdf3.dorado.jpa.policy.SaveContext;
import com.bstek.bdf3.dorado.jpa.policy.impl.SmartSavePolicyAdapter;
import com.bstek.dorado.data.provider.Page;

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
		Date createDate = (Date) param.get("createDate");
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
				.between("createDate", startDate, endDate)
			.endIf()
			.desc("createDate")
			.paging(page);
	}

	@Override
	@Transactional
	public void save(List<FileInfo> fileInfos) {
		JpaUtil.save(fileInfos, new SmartSavePolicyAdapter() {

			@Override
			public boolean beforeDelete(SaveContext context) {
				FileInfo fileInfo = context.getEntity();
				File file = new File(fileInfo.getPath());
				if (file.exists()) {
					boolean result = file.delete();
					if (!result) {
						throw new CustomException(SystemErrorEnum.FILE_DELETE_FAIL);
					}
				}
				int index = fileInfo.getPath().lastIndexOf(".");
				String previewFilePath = fileInfo.getPath().substring(0, index) + ".pdf";
				File previewFile = new File(previewFilePath);
				if (previewFile.exists()) {
					boolean result = previewFile.delete();
					if (!result) {
						throw new CustomException(SystemErrorEnum.FILE_DELETE_FAIL);
					}
				}
				return true;
			}

		});
	}
	
}
