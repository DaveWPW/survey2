package com.dave.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dave.common.vo.JsonResult;
import com.dave.entity.vo.PaperInfo;
import com.dave.entity.vo.QuesInfo;
import com.dave.entity.vo.ResultInfo;
import com.dave.service.PaperService;
import com.dave.service.SurveyService;

/**
 * Survey控制层(公有页面)
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
	@Autowired
    private PaperService paperService;
	
	/**
	 * 开始调查
	 * 
	 * @param paperName
	 * @param paperLanguage
	 * @return
	 */
	@RequestMapping("doStartSurvey")
	public JsonResult doStartSurvey(String paperName, String paperLanguage) {
		if(StringUtils.isEmpty(paperName)) {
    		return new JsonResult("问卷名称为空");
    	}
    	if(StringUtils.isEmpty(paperLanguage)) {
    		return new JsonResult("问卷语言为空");
    	}
		PaperInfo paperInfo = surveyService.findStartPaper(paperName, paperLanguage);
		if(paperInfo == null) {
			return new JsonResult("没有你想要的调查问卷！！");
		}
		return new JsonResult(paperInfo);
	}
	
	/**
	 * 根据问题ID集查询问题
	 * 
	 * @param quesIds
	 * @return
	 */
	@RequestMapping("doSelectQues")
    @ResponseBody
    public JsonResult doSelectQues(Integer... quesIds) {
    	if(StringUtils.isEmpty(quesIds) || quesIds.length <= 0) {
    		return new JsonResult("问题ID集为空");
    	}
    	List<QuesInfo> list = paperService.selectQuesByIds(quesIds);
    	return new JsonResult(list);
    }
	
	/**
	 * 提交调查结果
	 * 
	 * @param resultInfo
	 * @return
	 */
	@RequestMapping("doSubmitSurveyResult")
	public JsonResult doSubmitSurveyResult(ResultInfo resultInfo) {
		if(StringUtils.isEmpty(resultInfo)) {
    		return new JsonResult("提交对象为空");
    	}
		int row = surveyService.submitSurveyResult(resultInfo);
		if(row == 1) {
			return new JsonResult("Submit Succeed!", row);
		}
		return new JsonResult("Submit Failed!!");
	}
	
}