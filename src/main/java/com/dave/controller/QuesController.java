package com.dave.controller;

import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.dave.common.vo.JsonResult;
import com.dave.entity.vo.QuesInfo;
import com.dave.service.QuesService;

/**
 * Ques控制层
 * 
 * @author Dave20190826
 *
 */
@Controller
@RequestMapping("/ques/")
public class QuesController {
	@Autowired
    private QuesService quesService;
	
	/**
	 * 问题管理页面
	 * 
	 * @return system/ques_list
	 */
	@RequiresPermissions("S2")
    @RequestMapping("doQuesListUI")
	public String doQuesListUI(){
		return "system/ques_list";
	}
    
    /**
     * 问题编辑页面
     * 
     * @return system/ques_edit
     */
    @RequestMapping("doQuesEditUI")
	public String doQuesEditUI(){
		return "system/ques_edit";
	}
    
    /**
     * 查找查询所有问题
     * 
     * @param pageCurrent
     * @param quesName
     * @return
     */
    @RequestMapping("doFindQuesList")
    @ResponseBody
    public JsonResult doFindQuesList(Integer pageCurrent, String quesName) {
    	if(pageCurrent == null || pageCurrent <= 0) {
    		return new JsonResult("当前页面数不能为空");
    	}
    	return new JsonResult(quesService.findQuesList(pageCurrent, quesName));
    }
    
    /**
     * 添加问题
     * 
     * @param quesInfo
     * @return
     */
    @RequestMapping("doAddQues")
    @ResponseBody
    public JsonResult doAddQues(QuesInfo quesInfo) {
    	if(quesInfo == null) {
    		return new JsonResult("问题对象不能为空");
    	}
    	if(StringUtils.isEmpty(quesInfo.getQuesName())) {
    		return new JsonResult("问题名称不能为空");
    	}
    	if(StringUtils.isEmpty(quesInfo.getQuesType())) {
    		return new JsonResult("问题类型不能为空");
    	}
    	int row = quesService.addQues(quesInfo);
    	if(row == 1) {
    		return new JsonResult("Add Succeed!", row); 		
    	}
    	return new JsonResult("Add Failed!!");
    }
    
    /**
     * 删除问题
     * 
     * @param quesIds
     * @return
     */
    @RequestMapping("doDeleteQues")
    @ResponseBody
    public JsonResult doDeleteQues(Integer... quesIds) {
    	if(StringUtils.isEmpty(quesIds) || quesIds.length <= 0) {
    		return new JsonResult("问题ID集不能为空");
    	}
    	String resultInfo = quesService.deleteQues(quesIds);
    	if(resultInfo == null) {
    		return new JsonResult("Delete Succeed!", 1);	
    	}
    	return new JsonResult(resultInfo);
    }
    
    /**
     * 根据问题ID获取问题选项
     * 
     * @param quesId
     * @return
     */
    @RequestMapping("doGetQuesOption")
    @ResponseBody
    public JsonResult doGetQuesOption(Integer quesId) {
    	if(StringUtils.isEmpty(quesId)) {
    		return new JsonResult("问题ID不能为空");
    	}
    	QuesInfo quesInfo = quesService.getQuesOption(quesId);
    	return new JsonResult(quesInfo);
    }
    
    /**
     * 修改问题
     * 
     * @param quesInfo
     * @return
     */
    @RequestMapping("doUpdateQues")
    @ResponseBody
    public JsonResult doUpdateQues(QuesInfo quesInfo) {
    	if(quesInfo == null) {
    		return new JsonResult("问题对象不能为空");
    	}
    	if(StringUtils.isEmpty(quesInfo.getQuesId())) {
    		return new JsonResult("问题ID不能为空");
    	}
    	if(StringUtils.isEmpty(quesInfo.getQuesName())) {
    		return new JsonResult("问题名称不能为空");
    	}
    	if(StringUtils.isEmpty(quesInfo.getQuesType())) {
    		return new JsonResult("问题类型不能为空");
    	}
    	String resultInfo = quesService.updateQues(quesInfo);
    	if(resultInfo == null) {
    		return new JsonResult("Update Succeed!", 1);	
    	}
    	return new JsonResult(resultInfo);
    }
    
    /**
     * 跟进Excel报表导入问题
     * 
     * @param request
     * @return
     */
    @RequestMapping("doImportQues")
	@ResponseBody
	public JsonResult doImportQues(HttpServletRequest request) {
		try {
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
			if (multipartRequest != null) {
				Iterator<String> iterator = multipartRequest.getFileNames();
				while (iterator.hasNext()) {
	                MultipartFile file = multipartRequest.getFile(iterator.next());
	                if (StringUtils.hasText(file.getOriginalFilename())) {
	                	String resultInfo = quesService.importQues(file.getOriginalFilename(), file);
	                	if(resultInfo != null) {
	                		return new JsonResult(resultInfo, 0);
	                	}
	                }
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new JsonResult("Import Succeed!", 1);
	}
    
}