package com.dave.service;

import java.util.List;

import com.dave.common.vo.PageObject;
import com.dave.entity.Paper;
import com.dave.entity.PaperQuesOption;
import com.dave.entity.vo.PaperInfo;
import com.dave.entity.vo.QuesInfo;

/**
 * Paper业务层接口
 * 
 * @author Dave20190828
 *
 */
public interface PaperService {
	
	List<QuesInfo> selectQuesByIds(Integer... quesIds);
	
	int checkoutPaperName(String paperName, String paperLanguage, Integer paperId);
	
	int addPaper(PaperInfo paperInfo);
	
	PageObject<Paper> findPaperList(int pageCurrent, String paperName);
	
	int deletePaper(Integer... paperIds);
	
	int updateStatus(Paper paper);
	
	PaperInfo getPaperQues(int paperId);
	
	List<PaperQuesOption> getPaperQuesOption(int paperId);
	
	int updatePaper(PaperInfo paperInfo);
}
