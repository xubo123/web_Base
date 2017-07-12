package com.project.system.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellValue;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * excel工具类
 * 
 * @author Administrator
 * 
 */
public class ExcelUtil {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(ExcelUtil.class);

	static NumberFormat format = NumberFormat.getInstance();

	static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	/**
	 * 解析excel
	 * 
	 * @param file
	 * @return
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	public static List<Object[]> parseExcel(File file)
			throws FileNotFoundException, IOException {
		List<Object[]> list = new ArrayList<Object[]>();
		Workbook wb = ExcelUtil.createWorkbook(file);
		Sheet sheet = ExcelUtil.getSheet(wb, 0);
		FormulaEvaluator evaluator = wb.getCreationHelper()
				.createFormulaEvaluator();
		int rowStart = sheet.getFirstRowNum();
		int rowEnd = sheet.getLastRowNum();
		int firstColumn = 0;
		int lastColumn = 0;
		boolean columnValueFlag = false;// 判断一行中是否全部是空字符串
		for (int rowNum = rowStart; rowNum <= rowEnd; rowNum++) {
			Row r = sheet.getRow(rowNum);
			if (r == null) {
				logger.info("第" + rowNum + "行无数据!");
			} else {
				if (rowNum == rowStart) {
					firstColumn = r.getFirstCellNum();
					lastColumn = r.getLastCellNum();
				}
				Object[] array = new Object[lastColumn - firstColumn];
				for (int cn = firstColumn; cn < lastColumn; cn++) {
					Cell c = r.getCell(cn, Row.RETURN_BLANK_AS_NULL);
					if (c == null) {
						array[cn] = "";
					} else {
						array[cn] = getCellContents(c, evaluator);
						if (!columnValueFlag && array[cn] != null
								&& !"".equals(((String) array[cn]).trim())) {
							columnValueFlag = true;
						}
					}
				}
				if (columnValueFlag) {
					list.add(array);
					columnValueFlag = false;
				}
			}
		}
		return list;
	}

//	public static String exportData(List<Object[]> list) throws IOException {
//		// 文件保存目录路径
//		String savePath = PayConfig.disk_PATH;
//
//		// 文件保存目录URL
//		String saveUrl = PayConfig.file_Domain;
//		String dirName = "file";
//		savePath += dirName + "/";
//		saveUrl += dirName + "/";
//		File saveDirFile = new File(savePath);
//		if (!saveDirFile.exists()) {
//			saveDirFile.mkdirs();
//		}
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
//		String ymd = sdf.format(new Date());
//		savePath += ymd + "/";
//		saveUrl += ymd + "/";
//		File dirFile = new File(savePath);
//		if (!dirFile.exists()) {
//			dirFile.mkdirs();
//		}
//		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
//		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
//		String newFileName = df.format(new Date()) + "_"
//				+ new Random().nextInt(1000) + ".xls";
//		File file = new File(savePath, newFileName);
//		OutputStream out = new FileOutputStream(file);
//		Workbook wb = new HSSFWorkbook();
//		HSSFSheet sheet = (HSSFSheet) wb.createSheet();
//		HSSFFont titleFont = (HSSFFont) wb.createFont();
//		titleFont.setColor(HSSFColor.BLACK.index);
//		titleFont.setFontName("宋体");
//		titleFont.setFontHeightInPoints((short) 11);
//		HSSFCellStyle titleStyle = (HSSFCellStyle) wb.createCellStyle();
//		titleStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
//		titleStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
//		titleStyle
//				.setFillForegroundColor(HSSFColor.LIGHT_CORNFLOWER_BLUE.index);
//		titleStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
//		titleStyle.setFont(titleFont);
//		titleStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
//		titleStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
//		titleStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
//		titleStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
//		HSSFCellStyle columnStyle = (HSSFCellStyle) wb.createCellStyle();
//		columnStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
//		columnStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
//		columnStyle.setFont(titleFont);
//		columnStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
//		columnStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
//		columnStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
//		columnStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
//		if (list.size() > 1) {
//			for (int i = 0; i < list.size(); i++) {
//				HSSFRow row = sheet.createRow(i);
//				if (i == 0) {
//					row.setHeightInPoints(25);
//
//				} else {
//					row.setHeightInPoints(20);
//				}
//				int lenth = list.get(i).length;
//				for (int j = 0; j < lenth; j++) {
//					HSSFCell cell = row.createCell(j);
//					Object o = list.get(i)[j];
//					String str = "";
//					if (o instanceof Date) {
//						str = sdf1.format(o);
//					} else if (o instanceof Double ||o instanceof Integer) {
//						str = o.toString();
//					} else {
//						str = (String) o;
//					}
//					HSSFRichTextString text = new HSSFRichTextString(str);
//					cell.setCellValue(text);
//					sheet.setColumnWidth(j, 5000);
//					if (i == 0) {
//						cell.setCellStyle(titleStyle);
//					} else {
//						cell.setCellStyle(columnStyle);
//					}
//				}
//			}
//			wb.write(out);
//			out.close();
//			return saveUrl + newFileName;
//		}
//		return "";
//	}

	/**
	 * 创建工作簿
	 * 
	 * @param file
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static Workbook createWorkbook(File file)
			throws FileNotFoundException, IOException {
		String fileName = file.getName();
		String lastName = fileName.substring(fileName.lastIndexOf("."));
		Workbook wb = null;
		if (lastName.equals(".xls")) {
			wb = new HSSFWorkbook(new FileInputStream(file));
		}
		if (lastName.equals(".xlsx")) {
			wb = new XSSFWorkbook(new FileInputStream(file));
		}
		return wb;
	}

	/**
	 * 获取工作簿中sheet的个数
	 * 
	 * @param wb
	 * @return
	 */
	public static int getNumberOfSheets(Workbook wb) {
		return wb.getNumberOfSheets();
	}

	/**
	 * 根据sheet索引获取sheet
	 * 
	 * @param i
	 * @return
	 */
	public static Sheet getSheet(Workbook wb, int i) {
		return wb.getSheetAt(i);
	}

	/**
	 * excel数据遍历示例
	 * 
	 * @param sheet
	 * @return
	 */
	public Object demo(Sheet sheet) {
		int rowStart = sheet.getFirstRowNum();
		int rowEnd = sheet.getLastRowNum();
		for (int rowNum = rowStart; rowNum <= rowEnd; rowNum++) {
			Row r = sheet.getRow(rowNum);

			if (r == null) {
				logger.info("第" + rowNum + "行无数据!");
			} else {
				int firstColumn = r.getFirstCellNum();
				int lastColumn = r.getLastCellNum();
				for (int cn = firstColumn; cn < lastColumn; cn++) {
					Cell c = r.getCell(cn, Row.RETURN_BLANK_AS_NULL);
					if (c == null) {
						logger.info("第" + rowNum + "行" + cn + "列无数据!");
					} else {
						// System.out.println(getCellContents(c));
					}
				}
			}
		}
		return null;
	}

	/**
	 * 获取单元格的值
	 * 
	 * @param cell
	 */
	public static Object getCellContents(Cell cell, FormulaEvaluator evaluator) {
		if (cell != null) {
			switch (cell.getCellType()) {
			case Cell.CELL_TYPE_STRING:// 字符串型
				return cell.getRichStringCellValue().getString();
			case Cell.CELL_TYPE_NUMERIC:// 数值型
				if (DateUtil.isCellDateFormatted(cell)) {
					return sdf.format(cell.getDateCellValue());
				} else {
					double d = cell.getNumericCellValue();
					if (d - (int) d < Double.MIN_VALUE) {
						return new BigDecimal(d).toString();
					} else {
						format.setGroupingUsed(false);
						format.setMaximumFractionDigits(2);
						return format.format(d);
					}
				}
			case Cell.CELL_TYPE_BOOLEAN: // 布尔型
				return String.valueOf(cell.getBooleanCellValue());
			case Cell.CELL_TYPE_FORMULA:// 公式型
				CellValue cellValue = evaluator.evaluate(cell);
				switch (cellValue.getCellType()) {
				case Cell.CELL_TYPE_BOOLEAN:
					return String.valueOf(cellValue.getBooleanValue());
				case Cell.CELL_TYPE_NUMERIC:
					format.setGroupingUsed(false);
					format.setMaximumFractionDigits(2);
					return format.format(evaluator.evaluate(cell)
							.getNumberValue());
				case Cell.CELL_TYPE_STRING:
					return cellValue.getStringValue();
				default:
					return null;
				}
			default:
				return null;
			}
		}
		return null;
	}

	public static void main(String[] args) throws FileNotFoundException,
			IOException {
		// ExcelUtil excelUtil = new ExcelUtil();
		// File file = new File("E:/a.xlsx");
		// System.out.println(parseExcel(file).size());
		double d = 1.3862815E10;
		System.out.println((long) d);
	}
}
