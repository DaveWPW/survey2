package com.dave.common.util;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import java.text.NumberFormat;

/**
 * Excel工具类
 * 
 * @author Dave20190909
 *
 */
public class ExcelUtil {

	public static String getCellValue(Cell cell) {
		String cellValue = null;
		switch (cell.getCellType()) {
		case Cell.CELL_TYPE_NUMERIC:
			cellValue = NumberFormat.getInstance().format(cell.getNumericCellValue());
			break;
		case Cell.CELL_TYPE_STRING:
			cellValue = String.valueOf(cell.getStringCellValue());
			break;
		case Cell.CELL_TYPE_BOOLEAN:
			cellValue = String.valueOf(cell.getBooleanCellValue());
			break;
		case Cell.CELL_TYPE_FORMULA:
			cellValue = String.valueOf(cell.getCellFormula());
			break;
		case Cell.CELL_TYPE_BLANK:
			cellValue = "";
			break;
		case Cell.CELL_TYPE_ERROR:
			cellValue = "非法字符";
			break;
		default:
			cellValue = "未知类型";
			break;
		}
		return cellValue;
	}
	
	public static boolean isBlankRow(Row row){
        if(row == null) {
        	return true;
        }
        boolean result = true;
        for(int i = row.getFirstCellNum(); i < row.getLastCellNum(); i++){
        	Cell cell = row.getCell(i, Row.RETURN_BLANK_AS_NULL);
            String value = "";
            if(cell != null){
                switch (cell.getCellType()) {
                case Cell.CELL_TYPE_STRING:
                    value = cell.getStringCellValue();
                    break;
                case Cell.CELL_TYPE_NUMERIC:
                    value = String.valueOf((int) cell.getNumericCellValue());
                    break;
                case Cell.CELL_TYPE_BOOLEAN:
                    value = String.valueOf(cell.getBooleanCellValue());
                    break;
                case Cell.CELL_TYPE_FORMULA:
                    value = String.valueOf(cell.getCellFormula());
                    break;
                //case Cell.CELL_TYPE_BLANK:
                //    break;
                default:
                    break;
                }
                if(!value.trim().equals("")){
                    result = false;
                    break;
                }
            }
        }
        return result;
    }
}
