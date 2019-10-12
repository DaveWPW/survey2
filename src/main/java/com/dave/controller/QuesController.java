package com.dave.controller;

import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;

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
 * 调查问题Controller
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
	 * @return system/ques_list
	 */
    @RequestMapping("doQuesListUI")
	public String doQuesListUI(){
		return "system/ques_list";
	}
    /**
     * 问题编辑页面
     * @return system/ques_edit
     */
    @RequestMapping("doQuesEditUI")
	public String doQuesEditUI(){
		return "system/ques_edit";
	}
    /**
     * 查找问题数据
     * @return
     */
    @RequestMapping("doFindQuesList")
    @ResponseBody
    public JsonResult doFindQuesList(int pageCurrent, String quesName) {
    	return new JsonResult(quesService.findQuesList(pageCurrent, quesName));
    }
    /**
     * 添加问题
     * @param ques
     * @return
     */
    @RequestMapping("doAddQues")
    @ResponseBody
    public JsonResult doAddQues(QuesInfo quesInfo) {
    	int row = quesService.addQues(quesInfo);
    	if(row == 1) {
    		return new JsonResult("Add Succeed!", row); 		
    	}
    	return new JsonResult("Add Failed!!");
    }
    /**
     * 删除问题
     * @param quesIds
     * @return
     */
    @RequestMapping("doDeleteQues")
    @ResponseBody
    public JsonResult doDeleteQues(Integer... quesIds) {
    	String resultInfo = quesService.deleteQues(quesIds);
    	if(resultInfo == null) {
    		return new JsonResult("Delete Succeed!", 1);	
    	}
    	return new JsonResult(resultInfo);
    }
    /**
     * 根据问题获取选项
     * @param quesId
     * @return
     */
    @RequestMapping("doGetQuesOption")
    @ResponseBody
    public JsonResult doGetQuesOption(int quesId) {
    	QuesInfo quesInfo = quesService.getQuesOption(quesId);
    	return new JsonResult(quesInfo);
    }
    /**
     * 修改问题
     * @param ques
     * @return
     */
    @RequestMapping("doUpdateQues")
    @ResponseBody
    public JsonResult doUpdateQues(QuesInfo quesInfo) {
    	String resultInfo = quesService.updateQues(quesInfo);
    	if(resultInfo == null) {
    		return new JsonResult("Update Succeed!", 1);	
    	}
    	return new JsonResult(resultInfo);
    }
    
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
