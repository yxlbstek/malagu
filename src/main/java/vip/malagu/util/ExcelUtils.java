package vip.malagu.util;

import java.io.OutputStream;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.formula.functions.T;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.springframework.web.multipart.MultipartFile;

/**
 * Excel操作工具类
 * @author Lynn -- 2020年5月21日 下午5:12:06
 */
@SuppressWarnings("hiding")
public final class ExcelUtils {

	/**
	 * 导入Excel, 构造数据
	 * @param file 导入文件
	 * @param columnMap 属性与单元格标题关系
	 * @param clazz 导入对象类型
	 * @return 
	 * @throws Exception
	 */
	public static <T> List<T> importExcel(MultipartFile file, Map<String, String> columnMap, Class<T> clazz) throws Exception {		
		List<T> result = new ArrayList<T>();
		Workbook workbook = WorkbookFactory.create(file.getInputStream());
		List<Map<String, Object>> entityList = new ArrayList<Map<String, Object>>();
		int sheetSize = workbook.getNumberOfSheets();
		for (int i = 0; i < sheetSize; i++) {
			Sheet sheet = workbook.getSheetAt(i);
			int rowSize = sheet.getLastRowNum() + 1;
			for (int j = 1; j < rowSize; j++) {	// 遍历行  从1开始略过第一行标题行
				Row row = sheet.getRow(j);
				if (row == null) {
					continue;
				}
				Map<String, Object> entityMap = new HashMap<String, Object>();// 对应一个数据行
				int columnNum = 0;
				for (Entry<String, String> cm : columnMap.entrySet()) {
					Cell cell = row.getCell(columnNum);
					String value = null;
					if (cell != null) {
						value = getCellValue(cell);
					}
					entityMap.put(cm.getKey(), value);
					columnNum++;
				}
				entityList.add(entityMap);
			}
		}
		
		if (!entityList.isEmpty()) {
			Field[] fields = clazz.getDeclaredFields();
			for (Map<String, Object> map : entityList) {
				T obj = clazz.newInstance();
				for (Field field : fields) {
					if (map.get(field.getName()) != null) {
						String cellValue = map.get(field.getName()).toString();
						BeanUtils.setFieldValue(obj, field.getName(), BeanUtils.convertType(field.getType(), cellValue));
					}
				}
				result.add(obj);
			}
		}
		return result;
	}
	
	private static String getCellValue(Cell cell) {
		String cellvalue = null;
		switch (cell.getCellType()) {
			case HSSFCell.CELL_TYPE_NUMERIC: 
				short format = cell.getCellStyle().getDataFormat();
				if (format == 14 || format == 31 || format == 57 || format == 58) {
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					double value = cell.getNumericCellValue();
					Date date = DateUtil.getJavaDate(value);
					cellvalue = sdf.format(date);
				} else if (HSSFDateUtil.isCellDateFormatted(cell)) {
					Date date = cell.getDateCellValue();
					DateFormat formater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					cellvalue = formater.format(date);
				} else {
					cellvalue = NumberToTextConverter.toText(cell.getNumericCellValue());
				}	
			break;
		
			case HSSFCell.CELL_TYPE_STRING:
				cellvalue = cell.getStringCellValue().replaceAll("'", "''");
			break;
			
			case HSSFCell.CELL_TYPE_BLANK:
				cellvalue = null;
			break;
			default: {
				cellvalue = null;
			}
		}
		return cellvalue;
	}

	
	/**
	 * 导出Excel
	 * @param fileName 文件名称
	 * @param columnMap 导出列与属性的映射
	 * @param objs 导出的数据
	 * @param response
	 * @throws Exception
	 */
	public static <T> void exportExcel(String fileName, Map<String, String> columnMap, List<T> objs, HttpServletResponse response) throws Exception {
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("数据详情");
		sheet.setDefaultColumnWidth(20);
		HSSFRow row = sheet.createRow(0);
		row.setHeightInPoints(25);
		HSSFCellStyle titleStyle = createStyle(workbook);
		int index = 0;
		for (Entry<String, String> cloumn : columnMap.entrySet()) {
			HSSFCell cell = row.createCell(index);
			cell.setCellValue(cloumn.getValue());
			cell.setCellStyle(titleStyle);
			index++;
		}
		if (!objs.isEmpty()) {	
			for (int i = 0; i < objs.size(); i++) {
				row = sheet.createRow(i + 1);
				row.setHeightInPoints(20);
				HSSFCellStyle cellStyle = createCellStyle(workbook);
				int columnIndex = 0;
				for (Entry<String, String> cloumn : columnMap.entrySet()) {
					HSSFCell dataCell = row.createCell(columnIndex);
					dataCell.setCellStyle(cellStyle);
					Field[] fs = objs.get(i).getClass().getDeclaredFields();
					for (Field field : fs) {
						if (cloumn.getKey().equals(field.getName())) {
							String cellValue = "";
							Object fieldValue = BeanUtils.getFieldValue(objs.get(i), field);
							if (field.getType().getName().indexOf("Date") != -1) {
								cellValue = fieldValue != null ? DateUtils.dateToString((Date) fieldValue, "yyyy-MM-dd HH:mm:ss") : "";
							} else {
								cellValue = fieldValue != null ? fieldValue.toString() : "";
							}
							dataCell.setCellValue(cellValue);
						}
					}
					columnIndex++;
				}
			}
		}
		String fName = new String(fileName.getBytes("UTF-8"), "iso-8859-1");
		response.setContentType("application/x-msdownload");  
    	response.setCharacterEncoding("UTF-8");
    	response.setHeader("Content-Disposition", "attachment; filename=\"" + fName + "\"");
    	OutputStream out = response.getOutputStream();
    	workbook.write(out);
    	out.close();
	}
	
	public static HSSFCellStyle createStyle(HSSFWorkbook workbook) {
		HSSFCellStyle style = workbook.createCellStyle();
		style.setWrapText(true);
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		style.setBottomBorderColor(HSSFColor.BLACK.index);
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);

		HSSFFont headerFont = (HSSFFont) workbook.createFont();
		headerFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		headerFont.setFontName("黑体");
		headerFont.setFontHeightInPoints((short) 10);
		style.setFont(headerFont);
		
		return style;
	}
	
	public static HSSFCellStyle createCellStyle(HSSFWorkbook workbook) {
		HSSFCellStyle style = workbook.createCellStyle();
		style.setWrapText(true);
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		style.setBottomBorderColor(HSSFColor.BLACK.index);
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		return style;
	}

}
