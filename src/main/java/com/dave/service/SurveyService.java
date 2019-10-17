package com.dave.service;

import com.dave.entity.vo.PaperInfo;
import com.dave.entity.vo.ResultInfo;

/**
 * Survey业务层接口
 * 
 * @author Dave20190902
 *
 */
public interface SurveyService {
	
	PaperInfo findStartPaper(String paperName, String paperLanguage);
	
	int submitSurveyResult(ResultInfo resultInfo);
	
}
