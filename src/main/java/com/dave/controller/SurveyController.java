package com.dave.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dave.common.vo.JsonResult;
import com.dave.entity.vo.PaperInfo;
import com.dave.entity.vo.ResultInfo;
import com.dave.service.SurveyService;

/**
 * 调查问卷(用户使用)Controller
 * 
 * @author Dave20190902
 *
 */
@Controller
@ResponseBody
@RequestMapping("/survey/")
public class SurveyController {
	@Autowired
    private SurveyService surveyService;
	
	@RequestMapping("doStartSurvey")
	public JsonResult doStartSurvey(String paperName, String paperLanguage) {
		PaperInfo paperInfo = surveyService.findStartPaper(paperName, paperLanguage);
		if(paperInfo == null) {
			return new JsonResult("没有你想要的调查问卷！！");
		}
		return new JsonResult(paperInfo);
	}
	
	/**
	 * 条件调查结果
	 * 
	 * @param resultInfo
	 * @return
	 */
	@RequestMapping("doSubmitSurveyResult")
	public JsonResult doSubmitSurveyResult(ResultInfo resultInfo) {
		int row = surveyService.submitSurveyResult(resultInfo);
		if(row == 1) {
			return new JsonResult("Submit Succeed!", row);
		}
		return new JsonResult("Submit Failed!!");
	}
}
