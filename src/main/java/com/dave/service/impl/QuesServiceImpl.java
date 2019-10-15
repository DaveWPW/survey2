package com.dave.service.impl;

import java.io.InputStream;
import java.util.Date;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.dave.common.util.ExcelUtil;
import com.dave.common.vo.PageObject;
import com.dave.dao.QuesOptionDao;
import com.dave.dao.QuesDao;
import com.dave.entity.QuesOption;
import com.dave.entity.vo.QuesInfo;
import com.dave.entity.Ques;
import com.dave.service.QuesService;

/**
 * Ques业务层接口实现类
 * 
 * @author Dave20190826
 *
 */
@Transactional(rollbackFor=Throwable.class)
@Service
public class QuesServiceImpl implements QuesService {
	@Autowired
    private QuesDao quesDao;
	@Autowired
    private QuesOptionDao optionDao;
	@Override
	public PageObject<Ques> findQuesList(int pageCurrent, String quesName){
        int pageSize = 10;
        int startIndex = (pageCurrent-1) * pageSize;
        int rowCount = quesDao.getAllQuesCount();
        if(rowCount < startIndex){
            pageCurrent = 1;
            startIndex = (pageCurrent-1) * pageSize;
        }
        List<Ques> records = quesDao.findQuesList(startIndex, pageSize, quesName);
        PageObject<Ques> pageObject = new PageObject<>();
        pageObject.setPageCurrent(pageCurrent);
        pageObject.setRowCount(rowCount);
        pageObject.setPageSize(pageSize);
        pageObject.setRecords(records);
		return pageObject;
	}
	@Override
	public int addQues(QuesInfo quesInfo) {
		Ques ques = new Ques();
		ques.setQuesName(quesInfo.getQuesName());
		ques.setQuesType(quesInfo.getQuesType());
		ques.setMust(quesInfo.getMust());
		ques.setCreateTime(new Date());
		ques.setStatus(1);
		int row = quesDao.addQues(ques);
		if(row == 1) {
			for(int i = 0; i < quesInfo.getOptions().length; i++) {
				QuesOption option = new QuesOption();
				option.setQuesId(ques.getQuesId());
				option.setOptionContent(quesInfo.getOptions()[i]);
				option.setFlag(quesInfo.getFlags()[i]);
				option.setStatus(1);
				row = optionDao.addOption(option);
			}
		}
		return row;
	}
	@Override
	public String deleteQues(Integer... quesIds) {
		for(int quesId : quesIds) {
			optionDao.deleteOption(quesId);
			quesDao.deleteQues(quesId);
		}
		List<String> paperNameList = quesDao.findUsePaperNameByQuesIds(quesIds);
		if(paperNameList.size() == 0 || paperNameList == null) {
		} else {
			List<String> quesIdList = quesDao.findUseQuesIdByQuesIds(quesIds);
			return paperNameList.toString()+"调查问卷正在使用"+quesIdList.toString()+"编号的问题，拒绝删除"+quesIdList.toString()+"编号的问题！！";		
		}
		return null;
	}
	@Override
	public QuesInfo getQuesOption(int quesId) {
		Ques ques = quesDao.selectQuesById(quesId);
		QuesInfo quesInfo = new QuesInfo();
		quesInfo.setQuesId(quesId);
		quesInfo.setQuesName(ques.getQuesName());
		quesInfo.setQuesType(ques.getQuesType());
		quesInfo.setMust(ques.getMust());
		List<QuesOption> optionList = optionDao.selectOptionByQuesId(quesId);
		String[] options = new String[optionList.size()];
		Integer[] flags = new Integer[optionList.size()];
		for(int i = 0; i < optionList.size(); i++) {
			options[i] = optionList.get(i).getOptionContent();
			flags[i] = optionList.get(i).getFlag();
		}
		quesInfo.setOptions(options);
		quesInfo.setFlags(flags);
		return quesInfo;
	}
	@Override
	public String updateQues(QuesInfo quesInfo) {
		List<String> paperNameList = quesDao.findUsePaperNameByQuesIds(quesInfo.getQuesId());
		if(paperNameList.size() == 0 || paperNameList == null) {
		} else {
			return paperNameList.toString()+"调查问卷正在使用"+quesInfo.getQuesId()+"编号的问题，拒绝修改！！";	
		}
		Ques ques = new Ques();
		ques.setQuesId(quesInfo.getQuesId());
		ques.setQuesName(quesInfo.getQuesName());
		ques.setMust(quesInfo.getMust());
		int row = quesDao.updateQues(ques);
		if(row == 1) {
			row = optionDao.deleteOption(quesInfo.getQuesId());
			for(int i = 0; i < quesInfo.getOptions().length; i++) {
				QuesOption option = new QuesOption();
				option.setQuesId(quesInfo.getQuesId());
				option.setOptionContent(quesInfo.getOptions()[i]);
				option.setFlag(quesInfo.getFlags()[i]);
				option.setStatus(1);
				row = optionDao.addOption(option);
			}
		}
		if(row != 1) {
			return "Update Failed!!";
		}
		return null;
	}
	@Override
	public String importQues(String fileName, MultipartFile file) {
		if (!fileName.matches("^.+\\.(?i)(xls)$") && !fileName.matches("^.+\\.(?i)(xlsx)$")) {
			return "提示：上传文件格式不是xls和xlsx文件！！";
		}
		Workbook wb = null;
		Boolean isQues = true;
		try {
			InputStream is = file.getInputStream();
			wb = WorkbookFactory.create(is);
			Sheet sheet = wb.getSheetAt(0);
			int quesId = 0;
			for (int i = 0; i < sheet.getLastRowNum() + 1; i++) {
				Row row = sheet.getRow(i);
				if (row == null || ExcelUtil.isBlankRow(row)) {
					isQues = true;
					continue;
				}
				if(isQues) {
					Cell cell = row.getCell(0);
					Ques ques = new Ques();
					if (cell != null) {
						String cellValue = ExcelUtil.getCellValue(cell);
						ques.setQuesName(cellValue);
					} else {
						return "提示：第"+(i+1)+"行格式错误！！";
					}
					cell = row.getCell(1);
					if (cell != null) {
						String cellValue = ExcelUtil.getCellValue(cell);
						ques.setQuesType("0"+cellValue);
					} else {
						return "提示：第"+(i+1)+"行格式错误！！";
					}
					cell = row.getCell(2);
					if (cell != null) {
						String cellValue = ExcelUtil.getCellValue(cell);
						ques.setMust(Integer.parseInt(cellValue));
					} else {
						return "提示：第"+(i+1)+"行格式错误！！";
					}
					ques.setCreateTime(new Date());
					ques.setStatus(1);
					quesDao.addQues(ques);
					quesId = ques.getQuesId();
					isQues = false;
				} else {
					Cell cell = row.getCell(0);
					QuesOption option = new QuesOption();
					option.setQuesId(quesId);
					if (cell != null) {
						String cellValue = ExcelUtil.getCellValue(cell);
						option.setOptionContent(cellValue);
					} else {
						return "提示：第"+(i+1)+"行格式错误！！";
					}
					cell = row.getCell(1);
					if (cell != null) {
						String cellValue = ExcelUtil.getCellValue(cell);
						option.setFlag(Integer.parseInt(cellValue));
					} else {
						return "提示：第"+(i+1)+"行格式错误！！";
					}
					option.setStatus(1);
					optionDao.addOption(option);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
