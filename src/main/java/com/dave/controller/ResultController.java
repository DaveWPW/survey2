package com.dave.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dave.common.vo.JsonResult;
import com.dave.common.vo.PageObject;
import com.dave.entity.Result;
import com.dave.entity.ResultQues;
import com.dave.service.ResultService;

/**
 * Result控制层
 * 
 * @author Dave 20190909
 *
 */
@Controller
@RequestMapping("/result/")
public class ResultController {
	@Autowired
    private ResultService resultService;
	
	/**
	 * 调查结果管理页面
	 * 
	 * @return system/result_list
	 */
	@RequiresPermissions("S4")
    @RequestMapping("doResultListUI")
	public String doQuesListUI() {
		return "system/result_list";
	}
    /**
	 * 详细调查结果
	 * 
	 * @return system/result_detail
	 */
    @RequestMapping("doResultDetailUI")
	public String doResultDetailUI() {
		return "system/result_detail";
	}
    
	/**
	 * 查找查询所有调查结果
	 * 
	 * @param pageCurrent
	 * @param paperName
	 * @param startDate
	 * @param endDate
	 * @return
	 */
    @RequestMapping("doFindResultList")
    @ResponseBody
    public JsonResult doFindResultList(Integer pageCurrent, String paperName, String startDate, String endDate) {
    	if(pageCurrent == null || pageCurrent <= 0) {
    		return new JsonResult("当前页面数不能为空");
    	}
    	PageObject<Result> list = resultService.findResultList(pageCurrent, paperName, startDate, endDate);
    	return new JsonResult(list);
    }
    
    /**
     * 删除调查结果
     * 
     * @param resultIds
     * @return
     */
    @RequestMapping("doDeleteResult")
    @ResponseBody
    public JsonResult doDeleteResult(Integer... resultIds) {
    	if(StringUtils.isEmpty(resultIds) || resultIds.length <= 0) {
    		return new JsonResult("调查结果ID集不能为空");
    	}
    	int row = resultService.deleteResult(resultIds);
    	if(row > 0) {
    		return new JsonResult("Delete Succeed!", row); 		
    	}
    	return new JsonResult("Delete failed!!");
    }
    
    /**
     * 跟进调查结果ID获取调查结果问题
     * 
     * @param resultIds
     * @return
     */
    @RequestMapping("doGetResultQues")
    @ResponseBody
    public JsonResult doGetResultQues(Integer resultId) {
    	if(StringUtils.isEmpty(resultId)) {
    		return new JsonResult("问题ID不能为空");
    	}
    	List<ResultQues> list = resultService.getResultQues(resultId);
    	return new JsonResult(list);
    }
    
    /**
     * 以Excel报表导出调查结果
     * 
     * @param response
     * @param paperName
     * @param startDate
     * @param endDate
     */
	@RequestMapping("doExportResult")
	public void doExportResult(HttpServletResponse response, String paperName, String startDate, String endDate) {
		try {
			Workbook wb = resultService.exportResult(paperName, startDate, endDate);
			response.setContentType("application/vnd.ms-excel;charset=utf-8");
			OutputStream os = response.getOutputStream();
			Date currentTime = new Date();
			SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
			String dateStr = format.format(currentTime);
			response.setHeader("Content-disposition", "attachment;filename=survey_result_"+dateStr+".xls");// 默认Excel名称
			wb.write(os);
			os.flush();
			os.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}