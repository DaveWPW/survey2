package com.dave.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dave.common.util.JsonUtil;
import com.dave.common.vo.PageObject;
import com.dave.dao.QuesOptionDao;
import com.dave.dao.PaperQuesDao;
import com.dave.dao.PaperQuesOptionDao;
import com.dave.dao.PaperDao;
import com.dave.dao.QuesDao;
import com.dave.entity.QuesOption;
import com.dave.entity.vo.PaperInfo;
import com.dave.entity.vo.QuesInfo;
import com.dave.entity.Paper;
import com.dave.entity.PaperQues;
import com.dave.entity.PaperQuesOption;
import com.dave.entity.Ques;
import com.dave.service.PaperService;

/**
 * Paper业务层接口实现类
 * 
 * @author Dave20190828
 *
 */
@Transactional(rollbackFor=Throwable.class)
@Service
public class PaperServiceImpl implements PaperService {
	@Autowired
    private QuesDao quesDao;
	@Autowired
    private QuesOptionDao optionDao;
	@Autowired
	private PaperDao paperDao;
	@Autowired
	private PaperQuesDao paperQuesDao;
	@Autowired
	private PaperQuesOptionDao paperQuesOptionDao;
	
	@Override
	public List<QuesInfo> selectQuesByIds(Integer... quesIds) {
		List<QuesInfo> quesList = new ArrayList<>();
		for(int quesId : quesIds) {
			Ques ques = quesDao.selectQuesById(quesId);
			QuesInfo quesInfo = new QuesInfo();
			quesInfo.setQuesId(ques.getQuesId());
			quesInfo.setQuesName(ques.getQuesName());
			quesInfo.setQuesType(ques.getQuesType());
			quesInfo.setMust(ques.getMust());
			List<QuesOption> optionList = optionDao.selectOptionByQuesId(quesId);
			Integer[] optionIds = new Integer[optionList.size()];
			String[] options = new String[optionList.size()];
			Integer[] flags = new Integer[optionList.size()];
			for(int i = 0; i < optionList.size(); i++) {
				optionIds[i] = optionList.get(i).getOptionId();
				options[i] = optionList.get(i).getOptionContent();
				flags[i] = optionList.get(i).getFlag();
			}
			quesInfo.setOptionIds(optionIds);
			quesInfo.setOptions(options);
			quesInfo.setFlags(flags);
			quesList.add(quesInfo);
		}
		return quesList;
	}
	
	@Override
	public int checkoutPaperName(String paperName, String paperLanguage, Integer paperId) {
		int row = paperDao.checkoutPaperName(paperName, paperLanguage, paperId);
		return row;
	}
	
	@Override
	public int addPaper(PaperInfo paperInfo) {
		Paper paper = new Paper();
		paper.setPaperName(paperInfo.getPaperName());
		paper.setPaperType(paperInfo.getPaperType());
		paper.setPaperLanguage(paperInfo.getPaperLanguage());
		//使用状态，1：:使用，9：禁用
		paper.setStatus(9);
		paper.setCreateTime(new Date());
		paper.setPaperTitle(paperInfo.getPaperTitle());
		paper.setGreet(paperInfo.getGreet());
		paper.setThank(paperInfo.getThank());
		if("02".equals(paperInfo.getPaperType())){
			paper.setQuesSum(paperInfo.getQuesSum());
		}
		int row = paperDao.addPaper(paper);
		if(row == 1) {
			for(int i = 0; i < paperInfo.getQuesIds().length; i++) {
				PaperQues paperQues = new PaperQues();
				paperQues.setPaperId(paper.getPaperId());
				paperQues.setQuesNum(paperInfo.getQuesNum()[i]);
				paperQues.setQuesId(paperInfo.getQuesIds()[i]);
				row = paperQuesDao.addPaperQues(paperQues);
				if(row != 1) {
					return row;
				}
				if("02".equals(paperInfo.getPaperType())) {
					//将字符集数据转换为二维数组
					Integer[][] quesOption = JsonUtil.jsonStrOnIntArray(paperInfo.getQuesOptionStr());
					Integer[][] selectQues = JsonUtil.jsonStrOnIntArray(paperInfo.getSelectQuesStr());
					for(int j = 0; j < quesOption[i].length; j++) {
						PaperQuesOption paperQuesOption = new PaperQuesOption();
						paperQuesOption.setPaperId(paper.getPaperId());
						paperQuesOption.setQuesId(paperInfo.getQuesIds()[i]);
						paperQuesOption.setQuesType(paperInfo.getQuesTypes()[i]);
						paperQuesOption.setOptionId(quesOption[i][j]);
						paperQuesOption.setSelectNum(selectQues[i][j]);
						row = paperQuesOptionDao.addPaperQuesOption(paperQuesOption);
						if(row != 1) {
							return row;
						}
					}
				}
			}
		}
		return row;
	}

	@Override
	public PageObject<Paper> findPaperList(int pageCurrent, String paperName) {
        int pageSize = 10;
        int startIndex = (pageCurrent-1) * pageSize;
        int rowCount = paperDao.getAllPaperCount();
        if(rowCount < startIndex){
            pageCurrent = 1;
            startIndex = (pageCurrent-1) * pageSize;
        }
        List<Paper> records = paperDao.findPaperList(startIndex, pageSize, paperName);
        PageObject<Paper> pageObject = new PageObject<>();
        pageObject.setPageCurrent(pageCurrent);
        pageObject.setRowCount(rowCount);
        pageObject.setPageSize(pageSize);
        pageObject.setRecords(records);
		return pageObject;
	}

	@Override
	public int deletePaper(Integer... paperIds) {
		int rows = 0;
		for(int paperId : paperIds) {
			rows = paperDao.deletePaper(paperId);
		}
		return rows;
	}

	@Override
	public int updateStatus(Paper paper) {
		int row = paperDao.updateStatus(paper);
		return row;
	}

	@Override
	public PaperInfo getPaperQues(int paperId) {
		PaperInfo paperInfo = new PaperInfo();
		Paper paper = paperDao.selectPaperById(paperId);
		if(paper != null) {
			paperInfo.setPaperId(paperId);
			paperInfo.setPaperName(paper.getPaperName());
			paperInfo.setPaperType(paper.getPaperType());
			paperInfo.setPaperLanguage(paper.getPaperLanguage());
			paperInfo.setPaperTitle(paper.getPaperTitle());
			paperInfo.setGreet(paper.getGreet());
			paperInfo.setThank(paper.getThank());
			if("02".equals(paper.getPaperType())){
				paperInfo.setQuesSum(paper.getQuesSum());
			}
			List<PaperQues> quesList = paperQuesDao.selectQuesByPaperId(paperId);
			Integer[] quesIds = new Integer[quesList.size()];
			Integer[] quesNum = new Integer[quesList.size()];
			for(int i = 0; i < quesList.size(); i++) {
				quesIds[i] = quesList.get(i).getQuesId();
				quesNum[i] = quesList.get(i).getQuesNum();
			}
			paperInfo.setQuesIds(quesIds);
			paperInfo.setQuesNum(quesNum);
		}
		return paperInfo;
	}

	@Override
	public List<PaperQuesOption> getPaperQuesOption(int paperId) {
		List<PaperQuesOption> list = paperQuesOptionDao.getPaperQuesOption(paperId);
		return list;
	}
	
	@Override
	public int updatePaper(PaperInfo paperInfo) {
		Paper paper = new Paper();
		paper.setPaperId(paperInfo.getPaperId());
		paper.setPaperName(paperInfo.getPaperName());
		paper.setPaperLanguage(paperInfo.getPaperLanguage());
		paper.setPaperTitle(paperInfo.getPaperTitle());
		paper.setGreet(paperInfo.getGreet());
		paper.setThank(paperInfo.getThank());
		if("02".equals(paperInfo.getPaperType())){
			paper.setQuesSum(paperInfo.getQuesSum());
		}
		int row = paperDao.updatePaper(paper);
		if(row == 1) {
			paperQuesDao.deletePaperQues(paperInfo.getPaperId());
			paperQuesOptionDao.deletePaperQuesOption(paperInfo.getPaperId());
			for(int i = 0; i < paperInfo.getQuesIds().length; i++) {
				PaperQues paperQues = new PaperQues();
				paperQues.setPaperId(paperInfo.getPaperId());
				paperQues.setQuesNum(paperInfo.getQuesNum()[i]);
				paperQues.setQuesId(paperInfo.getQuesIds()[i]);
				row = paperQuesDao.addPaperQues(paperQues);
				if(row != 1) {
					return row;
				}
				if("02".equals(paperInfo.getPaperType())) {
					//将字符集数据转换为二维数组
					Integer[][] quesOption = JsonUtil.jsonStrOnIntArray(paperInfo.getQuesOptionStr());
					Integer[][] selectQues = JsonUtil.jsonStrOnIntArray(paperInfo.getSelectQuesStr());
					for(int j = 0; j < quesOption[i].length; j++) {
						PaperQuesOption paperQuesOption = new PaperQuesOption();
						paperQuesOption.setPaperId(paperInfo.getPaperId());
						paperQuesOption.setQuesId(paperInfo.getQuesIds()[i]);
						paperQuesOption.setQuesType(paperInfo.getQuesTypes()[i]);
						paperQuesOption.setOptionId(quesOption[i][j]);
						paperQuesOption.setSelectNum(selectQues[i][j]);
						row = paperQuesOptionDao.addPaperQuesOption(paperQuesOption);
						if(row != 1) {
							return row;
						}
					}
				}
			}
		}
		return row;
	}
	
}
