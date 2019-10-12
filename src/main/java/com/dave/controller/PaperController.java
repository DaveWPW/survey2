package com.dave.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
 * 调查问卷Controller
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
	 * @return system/paper_list
	 */
    @RequestMapping("doPaperListUI")
	public String doPaperListUI(){
		return "system/paper_list";
	}
    /**
     * 问卷编辑页面
     * @return system/paper_edit
     */
    @RequestMapping("doPaperEditUI")
	public String doPaperEditUI(){
		return "system/paper_edit";
	}
    /**
     * 选择问题页面
     * @return system/paper_select_ques
     */
    @RequestMapping("doSelectQuesUI")
	public String doSelectQuesUI(){
		return "system/paper_select_ques";
	}
    /**
     * 提交选取问题
     * @param paperIds
     * @return
     */
    @RequestMapping("doSelectQues")
    @ResponseBody
    public JsonResult doSelectQues(Integer... quesIds) {
    	List<QuesInfo> list = paperService.selectQuesByIds(quesIds);
    	return new JsonResult(list);
    }
    /**
     * 添加问卷
     * @param paperInfo
     * @return
     */
    @RequestMapping("doAddPaper")
    @ResponseBody
    public JsonResult doAddPaper(PaperInfo paperInfo) {
    	if(paperInfo == null) {
    		return new JsonResult("参数对象为空");
    	}
    	int rowName = paperService.checkoutPaperName(paperInfo.getPaperName(), paperInfo.getPaperLanguage(), paperInfo.getPaperId());
    	if(rowName > 0) {
    		return new JsonResult("该相同语言的问卷名称已存在！！");
    	}
    	int row = paperService.addPaper(paperInfo);
    	if(row == 1) {
    		return new JsonResult("Add Succeed!", row);
    	}
    	return new JsonResult("Add Failed!");
    }
    /**
     * 查找问卷数据
     * @param pageCurrent, paperName
     * @return
     */
    @RequestMapping("doFindPaperList")
    @ResponseBody
    public JsonResult doFindPaperList(int pageCurrent, String paperName) {
    	PageObject<Paper> paperList = paperService.findPaperList(pageCurrent, paperName);
    	return new JsonResult(paperList);
    }
    /**
     * 删除问卷
     * @param paperIds
     * @return
     */
    @RequestMapping("doDeletePaper")
    @ResponseBody
    public JsonResult doDeletePaper(Integer... paperIds) {
    	int row = paperService.deletePaper(paperIds);
    	if(row > 0) {
    		return new JsonResult("Delete Succeed!", row); 		
    	}
    	return new JsonResult("Delete Failed!");
    }
    /**
     * 更改问卷状态
     * @param paper
     * @return
     */
    @RequestMapping("doUpdateStatus")
    @ResponseBody
    public JsonResult doUpdateStatus(Paper paper) {
    	int row = paperService.updateStatus(paper);
    	if(row > 0) {
    		return new JsonResult("Update Succeed!", row); 		
    	}
    	return new JsonResult("Update Failed!");
    }
    /**
     * 根据问卷获取问题
     * @param quesId
     * @return
     */
    @RequestMapping("doGetPaperQues")
    @ResponseBody
    public JsonResult doGetPaperQues(int paperId) {
    	PaperInfo paperInfo = paperService.getPaperQues(paperId);
    	return new JsonResult(paperInfo);
    }
    /**
     * 根据问卷获取问题
     * @param quesId
     * @return
     */
    @RequestMapping("doGetPaperQuesOption")
    @ResponseBody
    public JsonResult doGetPaperQuesOption(int paperId) {
    	List<PaperQuesOption> list = paperService.getPaperQuesOption(paperId);
    	return new JsonResult(list);
    }
    /**
     * 
     * @param paperInfo
     * @return
     */
    @RequestMapping("doUpdatePaper")
    @ResponseBody
    public JsonResult doUpdatePaper(PaperInfo paperInfo) {
    	if(paperInfo == null) {
    		return new JsonResult("参数对象为空");
    	}
    	int rowName = paperService.checkoutPaperName(paperInfo.getPaperName(), paperInfo.getPaperLanguage(), paperInfo.getPaperId());
    	if(rowName > 0) {
    		return new JsonResult("该相同语言的问卷名称已存在！！");
    	}
    	int row = paperService.updatePaper(paperInfo);
    	if(row == 1) {
    		return new JsonResult("Update Succeed!", row); 		
    	}
    	return new JsonResult("Update Failed");
    }
}
