package com.dave.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dave.dao.PaperDao;
import com.dave.dao.PaperQuesDao;
import com.dave.dao.ResultDao;
import com.dave.dao.ResultQuesDao;
import com.dave.entity.Paper;
import com.dave.entity.PaperQues;
import com.dave.entity.Result;
import com.dave.entity.ResultQues;
import com.dave.entity.vo.PaperInfo;
import com.dave.entity.vo.ResultInfo;
import com.dave.service.SurveyService;

/**
 * Survey业务层接口实现类
 * 
 * @author Dave20190902
 *
 */
@Transactional(rollbackFor=Throwable.class)
@Service
public class SurveyServiceImpl implements SurveyService{
	@Autowired
	private PaperDao paperDao;
	@Autowired
	private PaperQuesDao paperQuesDao;
	@Autowired
	private ResultDao resultDao;
	@Autowired
	private ResultQuesDao resultQuesDao;
	@Override
	public PaperInfo findStartPaper(String paperName, String paperLanguage) {
		Paper paper = paperDao.findStartPaper(paperName, paperLanguage);
		if(paper == null) {
			return null;
		}
		PaperInfo paperInfo = new PaperInfo();
		paperInfo.setPaperId(paper.getPaperId());
		paperInfo.setPaperName(paperName);
		paperInfo.setPaperType(paper.getPaperType());
		paperInfo.setPaperTitle(paper.getPaperTitle());
		paperInfo.setGreet(paper.getGreet());
		paperInfo.setThank(paper.getThank());
		if("02".equals(paper.getPaperType())){
			paperInfo.setQuesSum(paper.getQuesSum());
		}
		List<PaperQues> quesList = paperQuesDao.selectQuesByPaperId(paper.getPaperId());
		Integer[] quesIds = new Integer[quesList.size()];
		Integer[] quesNum = new Integer[quesList.size()];
		for(int i = 0; i < quesList.size(); i++) {
			quesIds[i] = quesList.get(i).getQuesId();
			quesNum[i] = quesList.get(i).getQuesNum();
		}
		paperInfo.setQuesIds(quesIds);
		paperInfo.setQuesNum(quesNum);
		return paperInfo;
	}

	@Override
	public int submitSurveyResult(ResultInfo resultInfo) {
		try {
			Result result = new Result();
			result.setMobileNum(resultInfo.getMobileNum());
			result.setCli(resultInfo.getCli());
			result.setAgentId(resultInfo.getAgentId());
			result.setPaperName(resultInfo.getPaperName());
			result.setPaperId(resultInfo.getPaperId());
			result.setPaperType(resultInfo.getPaperType());
			result.setPaperLanguage(resultInfo.getPaperLanguage());
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
			Date inviteTime = simpleDateFormat.parse(resultInfo.getInviteTime());
			result.setInviteTime(inviteTime);
			result.setCreateTime(new Date());
			result.setStatus(1);
			int row = resultDao.addResult(result);
			if(row == 1) {
				for (int i = 0; i < resultInfo.getQuesNums().length; i++) {
					ResultQues resultQues = new ResultQues();
					resultQues.setResultId(result.getResultId());
					resultQues.setQuesNum(resultInfo.getQuesNums()[i]);
					resultQues.setQuesId(resultInfo.getQuesIds()[i]);
					resultQues.setQuesName(resultInfo.getQuesNames()[i]);
					resultQues.setQuesType(resultInfo.getQuesTypes()[i]);
					resultQues.setOptionContent(resultInfo.getOptionCons()[i]);
					row = resultQuesDao.addResultQues(resultQues);
				}
			}
			return row;
		} catch (ParseException e) {
			e.printStackTrace();
			return 0;
		}
	}
}
