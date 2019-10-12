package com.dave.entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 调查问卷PO
 * 
 * @author Dave20190828
 *
 */
public class Paper {
	/**问卷ID*/
	private Integer paperId;
	/**问卷名称*/
	private String paperName;
	/**问卷类型*/
	private String paperType;
	/**语言种型*/
	private String paperLanguage;
	/**使用状态*/
	private Integer status;
	/**创建日期*/
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	private Date createTime;
	/**创建修改*/
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	private Date modifyTime;
	/**问卷标题*/
	private String paperTitle;
	/**欢迎语句*/
	private String greet;
	/**感谢语句*/
	private String thank;
	/**分支作答总题数，用于分支问卷*/
	private Integer quesSum;
	
	public Integer getPaperId() {
		return paperId;
	}
	public void setPaperId(Integer paperId) {
		this.paperId = paperId;
	}
	public String getPaperName() {
		return paperName;
	}
	public void setPaperName(String paperName) {
		this.paperName = paperName;
	}
	public String getPaperType() {
		return paperType;
	}
	public void setPaperType(String paperType) {
		this.paperType = paperType;
	}
	public String getPaperLanguage() {
		return paperLanguage;
	}
	public void setPaperLanguage(String paperLanguage) {
		this.paperLanguage = paperLanguage;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getModifyTime() {
		return modifyTime;
	}
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}
	public String getPaperTitle() {
		return paperTitle;
	}
	public void setPaperTitle(String paperTitle) {
		this.paperTitle = paperTitle;
	}
	public String getGreet() {
		return greet;
	}
	public void setGreet(String greet) {
		this.greet = greet;
	}
	public String getThank() {
		return thank;
	}
	public void setThank(String thank) {
		this.thank = thank;
	}
	
	public Integer getQuesSum() {
		return quesSum;
	}
	public void setQuesSum(Integer quesSum) {
		this.quesSum = quesSum;
	}
}
