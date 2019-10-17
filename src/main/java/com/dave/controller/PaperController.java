package com.dave.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dave.common.vo.JsonResult;
import com.dave.common.vo.PageObject;
import com.dave.entity.Paper;
import com.dave.entity.PaperQuesOption;
import com.dave.entity.vo.PaperInfo;
import com.dave.entity.vo.QuesInfo;
import com.dave.service.PaperService;

/**
 * Paper控制层
 * 
 * @author Dave20190828
 *
 */
@Controller
@RequestMapping("/paper/")
public class PaperController {
	@Autowired
    private PaperService paperService;
	
	/**
	 * 问卷管理页面
	 * 
	 * @return system/paper_list
	 */
    @RequestMapping("doPaperListUI")
	public String doPaperListUI(){
		return "system/paper_list";
	}
    
    /**
     * 问卷编辑页面
     * 
     * @return system/paper_edit
     */
    @RequestMapping("doPaperEditUI")
	public String doPaperEditUI(){
		return "system/paper_edit";
	}
    
    /**
     * 选择问题页面
     * 
     * @return system/paper_select_ques
     */
    @RequestMapping("doSelectQuesUI")
	public String doSelectQuesUI(){
		return "system/paper_select_ques";
	}
    
    /**
     * 提交选取问题
     * 
     * @param paperIds
     * @return
     */
    @RequestMapping("doSelectQues")
    @ResponseBody
    public JsonResult doSelectQues(Integer... quesIds) {
    	if(StringUtils.isEmpty(quesIds) || quesIds.length <= 0) {
    		return new JsonResult("问题ID不能为空");
    	}
    	List<QuesInfo> list = paperService.selectQuesByIds(quesIds);
    	return new JsonResult(list);
    }
    
    /**
     * 添加问卷
     * 
     * @param paperInfo
     * @return
     */
    @RequestMapping("doAddPaper")
    @ResponseBody
    public JsonResult doAddPaper(PaperInfo paperInfo) {
    	if(paperInfo == null) {
    		return new JsonResult("问卷对象不能为空");
    	}
    	if(StringUtils.isEmpty(paperInfo.getPaperName())) {
    		return new JsonResult("问卷名称不能为空");
    	}
    	if(StringUtils.isEmpty(paperInfo.getPaperTitle())) {
    		return new JsonResult("问卷标题不能为空");
    	}
    	if(StringUtils.isEmpty(paperInfo.getPaperLanguage())) {
    		return new JsonResult("问卷语言不能为空");
    	}
    	if(StringUtils.isEmpty(paperInfo.getGreet())) {
    		return new JsonResult("问卷欢迎内容不能为空");
    	}
    	if(StringUtils.isEmpty(paperInfo.getPaperType())) {
    		return new JsonResult("问卷类型不能为空");
    	}
    	if(StringUtils.isEmpty(paperInfo.getThank())) {
    		return new JsonResult("问卷感谢内容不能为空");
    	}
    	if(StringUtils.isEmpty(paperInfo.getQuesIds()) || paperInfo.getQuesIds().length <= 0) {
    		return new JsonResult("问题ID集不能为空");
    	}
    	if(StringUtils.isEmpty(paperInfo.getQuesNum()) || paperInfo.getQuesNum().length <= 0) {
    		return new JsonResult("问题序号不能为空");
    	}
    	int rowName = paperService.checkoutPaperName(paperInfo.getPaperName(), paperInfo.getPaperLanguage(), paperInfo.getPaperId());
    	if(rowName > 0) {
    		return new JsonResult("该相同语言的问卷名称已存在！！");
    	}
    	int row = paperService.addPaper(paperInfo);
    	if(row == 1) {
    		return new JsonResult("Add Succeed!", row);
    	}
    	return new JsonResult("Add Failed!！");
    }
    
    /**
     * 查找问卷数据
     * 
     * @param pageCurrent
     * @param paperName
     * @return
     */
    @RequestMapping("doFindPaperList")
    @ResponseBody
    public JsonResult doFindPaperList(Integer pageCurrent, String paperName) {
    	if(pageCurrent == null || pageCurrent <= 0) {
    		return new JsonResult("当前页面数不能为空");
    	}
    	PageObject<Paper> paperList = paperService.findPaperList(pageCurrent, paperName);
    	return new JsonResult(paperList);
    }
    
    /**
     * 删除问卷
     * 
     * @param paperIds
     * @return
     */
    @RequestMapping("doDeletePaper")
    @ResponseBody
    public JsonResult doDeletePaper(Integer... paperIds) {
    	if(StringUtils.isEmpty(paperIds) || paperIds.length > 0) {
    		return new JsonResult("问卷ID集不能为空");
    	}
    	int row = paperService.deletePaper(paperIds);
    	if(row > 0) {
    		return new JsonResult("Delete Succeed!", row); 		
    	}
    	return new JsonResult("Delete Failed!！");
    }
    
    /**
     * 更改问卷状态
     * 
     * @param paper
     * @return
     */
    @RequestMapping("doUpdateStatus")
    @ResponseBody
    public JsonResult doUpdateStatus(Paper paper) {
    	if(paper == null) {
    		return new JsonResult("问卷对象不能为空");
    	}
    	if(StringUtils.isEmpty(paper.getStatus())) {
    		return new JsonResult("问卷状态不能为空");
    	}
    	int row = paperService.updateStatus(paper);
    	if(row > 0) {
    		return new JsonResult("Update Succeed!", row); 		
    	}
    	return new JsonResult("Update Failed!！");
    }
    
    /**
     * 根据问卷ID获取问题
     * 
     * @param paperId
     * @return
     */
    @RequestMapping("doGetPaperQues")
    @ResponseBody
    public JsonResult doGetPaperQues(Integer paperId) {
    	if(StringUtils.isEmpty(paperId)) {
    		return new JsonResult("问卷ID不能为空");
    	}
    	PaperInfo paperInfo = paperService.getPaperQues(paperId);
    	return new JsonResult(paperInfo);
    }
    
    /**
     *  根据问卷ID获取问题
     * 
     * @param paperId
     * @return
     */
    @RequestMapping("doGetPaperQuesOption")
    @ResponseBody
    public JsonResult doGetPaperQuesOption(Integer paperId) {
    	if(StringUtils.isEmpty(paperId)) {
    		return new JsonResult("问卷ID不能为空");
    	}
    	List<PaperQuesOption> list = paperService.getPaperQuesOption(paperId);
    	return new JsonResult(list);
    }
    
    /**
     * 修改问卷
     * 
     * @param paperInfo
     * @return
     */
    @RequestMapping("doUpdatePaper")
    @ResponseBody
    public JsonResult doUpdatePaper(PaperInfo paperInfo) {
    	if(paperInfo == null) {
    		return new JsonResult("问卷对象不能为空");
    	}
    	if(StringUtils.isEmpty(paperInfo.getPaperId())) {
    		return new JsonResult("问卷ID不能为空");
    	}
    	if(StringUtils.isEmpty(paperInfo.getPaperName())) {
    		return new JsonResult("问卷名称不能为空");
    	}
    	if(StringUtils.isEmpty(paperInfo.getPaperTitle())) {
    		return new JsonResult("问卷标题不能为空");
    	}
    	if(StringUtils.isEmpty(paperInfo.getPaperLanguage())) {
    		return new JsonResult("问卷语言不能为空");
    	}
    	if(StringUtils.isEmpty(paperInfo.getGreet())) {
    		return new JsonResult("问卷欢迎内容不能为空");
    	}
    	if(StringUtils.isEmpty(paperInfo.getPaperType())) {
    		return new JsonResult("问卷类型不能为空");
    	}
    	if(StringUtils.isEmpty(paperInfo.getThank())) {
    		return new JsonResult("问卷感谢内容不能为空");
    	}
    	if(StringUtils.isEmpty(paperInfo.getQuesIds()) || paperInfo.getQuesIds().length <= 0) {
    		return new JsonResult("问题ID集不能为空");
    	}
    	if(StringUtils.isEmpty(paperInfo.getQuesNum()) || paperInfo.getQuesNum().length <= 0) {
    		return new JsonResult("问题序号不能为空");
    	}
    	int rowName = paperService.checkoutPaperName(paperInfo.getPaperName(), paperInfo.getPaperLanguage(), paperInfo.getPaperId());
    	if(rowName > 0) {
    		return new JsonResult("该相同语言的问卷名称已存在！！");
    	}
    	int row = paperService.updatePaper(paperInfo);
    	if(row == 1) {
    		return new JsonResult("Update Succeed!", row); 		
    	}
    	return new JsonResult("Update Failed！！");
    }
}
