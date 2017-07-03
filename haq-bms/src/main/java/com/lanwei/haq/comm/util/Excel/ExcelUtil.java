package com.lanwei.haq.comm.util.Excel;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ExcelUtil {

	public static final Logger logger = LoggerFactory.getLogger(ExcelUtil.class);

	public enum pStyle{
		/**
		 * 处理错误
		 */
		DoError,
		/**
		 * 不处理错误
		 */
		NoDoError;
	}

	private int sheet;					//指定sheet块
	private boolean pType; 				//cellNum或cellName对应标识（true :cellNums false:cellName）
	private pStyle style;				//错误处理标识


	public ExcelUtil(){}
	/*
	 * 指定sheet
	 */
	public ExcelUtil(int sheet,pStyle pstyle){
		this.sheet = sheet;
		this.pType = true;
		this.style = pstyle;
	}
	/*
	 * 指定对应方式（cellNums/cellName）
	 */
	public ExcelUtil(boolean pType,pStyle pstyle){
		this.sheet = 0;
		this.pType = pType;
		this.style = pstyle;
	}
	/*
	 * 指定sheet,对应方式
	 */
	public ExcelUtil(int sheet,boolean pType,pStyle pstyle){
		this.sheet = sheet;
		this.pType = pType;
		this.style = pstyle;
	}
	/**
	 * @描述 指定sheet,默认cellNums处理
	 * @param sheet
	 * @param pstyle
	 * @return
	 */
	public static ExcelUtil init(int sheet,pStyle pstyle){
		return new ExcelUtil(sheet,pstyle);
	}
	/**
	 * @描述 指定处理方式cellNums(true)/cellName(false),默认sheet 0
	 * @param pType
	 * @param pstyle
	 * @return
	 */
	public static ExcelUtil init(boolean pType,pStyle pstyle){
		return new ExcelUtil(pType,pstyle);
	}
	/**
	 * @描述 指定sheet,指定处理方式cellNums(true)/cellName(false)
	 * @param sheet
	 * @param pType
	 * @param pstyle
	 * @return
	 */
	public static ExcelUtil init(int sheet,boolean pType,pStyle pstyle){
		return new ExcelUtil(sheet,pType,pstyle);
	}

	/**
	 * @描述 指定开始行，默认至最后一行，第一列至最后一列-- XLS
	 * @param is
	 * @param startRow
	 * @param clazz
	 * @return
	 */
	public <T> List<T> parseExcelXLS(InputStream is,int startRow,Class<T> clazz){
		List<T> list = new ArrayList<T>();
		try {
			Workbook wb = new HSSFWorkbook(is);
			Sheet sheet = wb.getSheetAt(this.sheet);
			list = parseExcel(sheet,startRow,-1,clazz);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return list;
	}
	/**
	 * @描述 指定开始行，默认至最后一行，第一列至指定列 --XLS
	 * @param is
	 * @param startRow
	 * @param endCol
	 * @param clazz
	 * @return
	 */
	public <T> List<T> parseExcelXLS(InputStream is,int startRow, int endCol,Class<T> clazz){
		List<T> list = new ArrayList<T>();
		try {
			Workbook wb = new HSSFWorkbook(is);
			Sheet sheet = wb.getSheetAt(this.sheet);
			list = parseExcel(sheet,startRow,endCol,clazz);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return list;
	}
	/**
	 * @描述 指定开始行，默认至最后一行，第一列至最后一列-- XLSX
	 * @param is
	 * @param startRow
	 * @param clazz
	 * @return
	 */
	public <T> List<T> parseExcelXLSX(InputStream is,int startRow,Class<T> clazz){
		List<T> list = new ArrayList<T>();
		try {
			Workbook wb = new XSSFWorkbook(is);
			Sheet sheet = wb.getSheetAt(this.sheet);
			list = parseExcel(sheet,startRow,-1,clazz);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return list;
	}
	/**
	 * @描述 指定开始行，默认至最后一行，第一列至指定列 --XLSX
	 * @param is
	 * @param startRow
	 * @param endCol
	 * @param clazz
	 * @return
	 */
	public <T> List<T> parseExcelXLSX(InputStream is,int startRow, int endCol,Class<T> clazz){
		List<T> list = new ArrayList<T>();
		try {
			Workbook wb = new XSSFWorkbook(is);
			Sheet sheet = wb.getSheetAt(this.sheet);
			list = parseExcel(sheet,startRow,endCol,clazz);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * @描述 通用Excel解析类（被调用方法）
	 * @param sheet
	 * @param startRow
	 * @param endCol
	 * @param clazz
	 * @return
	 */
	private <T> List<T> parseExcel(Sheet sheet,int startRow, int endCol, Class<T> clazz){
		List<T> list = new ArrayList<T>();
		Row row = null;
		Cell cell = null;
		T obj = null;
		// 开始行
		startRow = startRow == -1 ? sheet.getFirstRowNum() + 1 : startRow;
		for(int i = startRow; i <= sheet.getLastRowNum(); i++){
			try{
				obj = clazz.newInstance();
				row = sheet.getRow(i);
				if (null != row){
					//结束列
					endCol = endCol == -1 ? row.getPhysicalNumberOfCells() : endCol;
					for(int j=0;j<endCol;j++){
						cell = row.getCell(j);
						setEntity(startRow,obj,cell);
					}
				}
				list.add(obj);
			}catch (Exception e){
				switch(style){
					case DoError:
						return list;
					case NoDoError:
						return null;
				}
				logger.error("--------------数据解析失败----------------");
			}
		}
		return list;
	}

	/**
	 * @描述 实体赋值
	 * @param startRow
	 * @param entity
	 * @param cell
	 * @throws Exception
	 */
	private void setEntity(int startRow,Object entity,Cell cell) throws Exception{
		Field[] fields = Class.forName(entity.getClass().getName()).getDeclaredFields();
		if(null != cell){
			try{
				String value = null;
				String titlename = cell.getSheet().getRow(startRow-1).getCell(cell.getColumnIndex()).getStringCellValue();
				if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC && HSSFDateUtil.isCellDateFormatted(cell)){
					value = cell.getDateCellValue().getTime() + "";
				}else{
					cell.setCellType(Cell.CELL_TYPE_STRING);
					value = cell.getStringCellValue();
				}
				if(null != value || !"".equals(value)){
					for (Field field : fields){
						// 设置属性可以修改
						field.setAccessible(true);
						if(field.isAnnotationPresent(Excel.class)){
							Excel excel = field.getAnnotation(Excel.class);
							if(pType){
								if(cell.getColumnIndex() == excel.cellNums()){
									field.set(entity, value);
								}
							}else{
								if(titlename.equals(excel.cellName())){
									field.set(entity, value);
								}
							}
						}
					}
				}
			}catch (Exception e){
				logger.error("--------------数据解析错误----------------");
			}
		}
	}

	/**
	 * @描述 excel 导出
	 * @param out
	 * @param sheetName
	 * @param datas
	 */
	public static <T> void exportExcel(OutputStream out, String sheetName, List<T> datas, Class<T> clazz){
		try {
			T entity = clazz.newInstance();
			Field[] fields = Class.forName(entity.getClass().getName()).getDeclaredFields();
			String oldSheetName = sheetName;
			// 产生工作簿对象
			HSSFWorkbook workbook = new HSSFWorkbook();
			// 产生工作表对象
			int sheetNum = 1;
			Sheet sheet = workbook.createSheet(sheetName + "-" + sheetNum++);
			// 进行转码，使其支持中文文件名
			sheetName = URLEncoder
					.encode(sheetName+new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()),"UTF-8");
			generateExcelHeader(sheet,workbook,fields);
			// 插入数据
			int sheetLength = 0;
			int rowNum = 1;
			for (int i = 0; i < datas.size();i++) {
				int colNum = 0;
				T obj = datas.get(i);
				if (obj != null) {
					// 加入超过单个Sheet的最大长度，则产生一个新的sheet页
					sheetLength++;
					if (sheetLength >= 65535) {
						sheetLength = 0;
						rowNum = 1;
						sheet = workbook.createSheet(oldSheetName + "-"+ sheetNum++);
						generateExcelHeader(sheet,workbook,fields);
					}
					Row row = sheet.createRow(rowNum++);// 创建一行
					for(int j=0;j<fields.length;j++){
						Field field = fields[j];
						Object value=new Object();
						field.setAccessible(true);
						if(field.isAnnotationPresent(Excel.class)){
							String fieldType = field.getType().getSimpleName();
							Cell cell = row.createCell(colNum);// 创建一列
							if("Date".equals(fieldType)){
								cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
								value = field.get(obj);
								SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
								value=format.format(value);
							}else if("Integer".equals(fieldType) || "int".equals(fieldType)){
								cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
								value = field.get(obj);
							}else{
								cell.setCellType(HSSFCell.CELL_TYPE_STRING);
								value = String.valueOf(field.get(obj));
							}
							setCellValue(cell, value, cell.getCellType());
							colNum++;
						}
					}
				}
			}
			workbook.write(out);
			out.flush();
		}catch(Exception e) {
			e.printStackTrace();
		}finally{
			try{
				if (out != null){
					out.close();
				}
			}catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	/**
	 * @描述 生成公共头部信息
	 * @param sheet
	 * @param workbook
	 */
	public static void generateExcelHeader(Sheet sheet,HSSFWorkbook workbook,Field[] fields) {
		int colNum = 0;
		// 单元格样式
		CellStyle headerStyle = workbook.createCellStyle();
		// 字体样式
		HSSFFont font = workbook.createFont();
		font.setFontHeightInPoints((short) 12);
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		headerStyle.setFont(font);
		// 生成头行
		Row row = sheet.createRow(0);// 创建一行
		for(int i=0;i<fields.length;i++){
			Field field = fields[i];
			field.setAccessible(true);
			if(field.isAnnotationPresent(Excel.class)){
				Cell cell = row.createCell(colNum);// 创建一列
				Excel excel = field.getAnnotation(Excel.class);
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell.setCellValue(excel.cellName());
				cell.setCellStyle(headerStyle);
				//sheet.autoSizeColumn(i);
				sheet.setColumnWidth(colNum,(short)(35.7*120));
				colNum++;
			}
		}
	}
	/**
	 * @描述 生成Excel响应流
	 * @param response
	 * @param datas		表格数据
	 */
	public static <T> void ExcelExport(HttpServletResponse response, List<T> datas, Class<T> clazz) {
		//格式化当前时间作为文件名
		Date local = new Date();
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
		String fileName = df.format(local);
		// 设置响应头
		response.setCharacterEncoding("utf-8");
		response.setContentType("multipart/form-data");
		try {
			response.setHeader("Content-Disposition",
					"attachment;fileName=" + URLEncoder.encode(fileName + ".xls", "UTF-8"));
		} catch (Exception e) {
			response.setHeader("Content-Disposition", "attachment;fileName=" + "download.xls");
		}
		try {
			exportExcel(response.getOutputStream(), fileName, datas, clazz);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * @描述 设置单元格的值
	 * @param cell 单元格
	 * @param value 值
	 * @param cellType 单元格类型
	 */
	public static void setCellValue(Cell cell, Object value, int cellType) {
		if (cellType == HSSFCell.CELL_TYPE_STRING) {
			cell.setCellValue((String)value);
		} else if (cellType == HSSFCell.CELL_TYPE_NUMERIC) {
			cell.setCellValue((double)value);
		} else if (cellType == HSSFCell.CELL_TYPE_BOOLEAN) {
			cell.setCellValue((Boolean) value);
		} else if (cellType == HSSFCell.CELL_TYPE_BLANK) {
			cell.setCellValue("");
		}
	}

}