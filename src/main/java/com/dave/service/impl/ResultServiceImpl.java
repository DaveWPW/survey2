package com.dave.service.impl;

import java.text.SimpleDateFormat;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dave.common.vo.PageObject;
import com.dave.dao.ResultDao;
import com.dave.dao.ResultQuesDao;
import com.dave.entity.Result;
import com.dave.entity.ResultQues;
import com.dave.entity.vo.ResultExportInfo;
import com.dave.service.ResultService;

/**
 * Result业务层接口实现类
 * 
 * @author Dave20190910
 *
 */
@Transactional(rollbackFor=Throwable.class)
@Service
public class ResultServiceImpl implements ResultService {
	@Autowired
    private ResultDao resultDao;
	@Autowired
	private ResultQuesDao resultQuesDao;
	@Override
	public PageObject<Result> findResultList(int pageCurrent, String paperName, String startDate, String endDate) {
        int pageSize = 10;
        int startIndex = (pageCurrent-1) * pageSize;
        int rowCount = resultDao.getAllResultCount();
        if(rowCount < startIndex){
            pageCurrent = 1;
            startIndex = (pageCurrent-1) * pageSize;
        }
        List<Result> records = resultDao.findResultList(startIndex, pageSize, paperName, startDate, endDate);
        PageObject<Result> pageObject = new PageObject<>();
        pageObject.setPageCurrent(pageCurrent);
        pageObject.setRowCount(rowCount);
        pageObject.setPageSize(pageSize);
        pageObject.setRecords(records);
		return pageObject;
	}
	@Override
	public int deleteResult(Integer... resultIds) {
		int rows = 0;
		for(int resultId : resultIds) {
			rows = resultDao.deleteResult(resultId);
		}
		return rows;
	}
	@Override
	public List<ResultQues> getResultQues(int resultId) {
		List<ResultQues> resultQuesList = resultQuesDao.selectResultQuesById(resultId);
		return resultQuesList;
	}
	@Override
	public Workbook exportResult(String paperName, String startDate, String endDate) {
		List<ResultExportInfo> resultList = resultDao.exportResult(paperName, startDate, endDate);
		HSSFWorkbook wb = new HSSFWorkbook();
		Sheet sheet = wb.createSheet("Survey Result");
		
	    HSSFFont font = wb.createFont();//生成字体
	    font.setColor(HSSFColor.WHITE.index);//设置字体颜色
	    font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);//设置字体增粗
	    
	    HSSFCellStyle style = wb.createCellStyle();//生成单元格样式
	    style.setFillForegroundColor(HSSFColor.TEAL.index);//设置背景颜色
	    style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);//solid填充foreground前景色
	    style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//垂直
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);//水平 
        style.setFont(font);//把字体应用到当前的样式
	    
		Row row = null;
		row = sheet.createRow(0);
		
		Cell cell = row.createCell(0);
		cell.setCellStyle(style);
		cell.setCellValue("Paper Name");
		cell = row.createCell(1);
		cell.setCellStyle(style);
		cell.setCellValue("Mobile Number");
		cell = row.createCell(2);
		cell.setCellStyle(style);
		cell.setCellValue("CLI");
		cell = row.createCell(3);
		cell.setCellStyle(style);
		cell.setCellValue("Agent ID");
		cell = row.createCell(4);
		cell.setCellStyle(style);
		cell.setCellValue("P Type");
		cell = row.createCell(5);
		cell.setCellStyle(style);
		cell.setCellValue("Language");
		cell = row.createCell(6);
		cell.setCellStyle(style);
		cell.setCellValue("No");
		cell = row.createCell(7);
		cell.setCellStyle(style);
		cell.setCellValue("Q Type");
		cell = row.createCell(8);
		cell.setCellStyle(style);
		cell.setCellValue("Question");
		cell = row.createCell(9);
		cell.setCellStyle(style);
		cell.setCellValue("Answer");
		cell = row.createCell(10);
		cell.setCellStyle(style);
		cell.setCellValue("Time");
		for (int i = 0; i < resultList.size(); i++) {
			row = sheet.createRow(i + 1);
			ResultExportInfo info = resultList.get(i);
			row.createCell(0).setCellValue(info.getPaperName());
			row.createCell(1).setCellValue(info.getMobileNum());
			row.createCell(2).setCellValue(info.getCli());
			row.createCell(3).setCellValue(info.getAgentId());
			if("01".equals(info.getPaperType())) {
				info.setPaperType("单张");
			}else if("02".equals(info.getPaperType())) {
				info.setPaperType("分支");
			}
			row.createCell(4).setCellValue(info.getPaperType());
			if("ch".equals(info.getPaperLanguage())) {
				info.setPaperLanguage("中文");
			}else if("eng".equals(info.getPaperLanguage())) {
				info.setPaperLanguage("英文");
			}
			row.createCell(5).setCellValue(info.getPaperLanguage());
			row.createCell(6).setCellValue(info.getQuesNum());
			if("01".equals(info.getQuesType())) {
				info.setQuesType("单选题");
			}else if("02".equals(info.getQuesType())) {
				info.setQuesType("多选题");
			}else if("03".equals(info.getQuesType())) {
				info.setQuesType("简答题");
			}
			row.createCell(7).setCellValue(info.getQuesType());
			row.createCell(8).setCellValue(info.getQuesName());
			row.createCell(9).setCellValue(info.getOptionContent());
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String dateStr = dateFormat.format(info.getCreateTime());
			row.createCell(10).setCellValue(dateStr);
		}
		for (int i = 0; i <= 10; i++) {
			sheet.autoSizeColumn(i);
		}
		return wb;
	}
}
