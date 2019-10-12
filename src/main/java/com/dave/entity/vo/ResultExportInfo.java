package com.dave.entity.vo;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class ResultExportInfo {
	private String paperName;
	private Long mobileNum;
	private Long cli;
	private Integer agentId;
	private String paperType;
	private String paperLanguage;
	private Integer quesNum;
	private String quesType;
	private String quesName;
	private String optionContent;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	private Date createTime;
	
	@Override
	public String toString() {
		return "ResultExportInfo [paperName=" + paperName + ", mobileNum=" + mobileNum + ", cli=" + cli + ", agentId="
				+ agentId + ", paperType=" + paperType + ", paperLanguage=" + paperLanguage + ", quesNum=" + quesNum
				+ ", quesType=" + quesType + ", quesName=" + quesName + ", optionContent=" + optionContent
				+ ", createTime=" + createTime + "]";
	}
	public String getPaperName() {
		return paperName;
	}
	public void setPaperName(String paperName) {
		this.paperName = paperName;
	}
	public Long getMobileNum() {
		return mobileNum;
	}
	public void setMobileNum(Long mobileNum) {
		this.mobileNum = mobileNum;
	}
	public Long getCli() {
		return cli;
	}
	public void setCli(Long cli) {
		this.cli = cli;
	}
	public Integer getAgentId() {
		return agentId;
	}
	public void setAgentId(Integer agentId) {
		this.agentId = agentId;
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
	public Integer getQuesNum() {
		return quesNum;
	}
	public void setQuesNum(Integer quesNum) {
		this.quesNum = quesNum;
	}
	public String getQuesType() {
		return quesType;
	}
	public void setQuesType(String quesType) {
		this.quesType = quesType;
	}
	public String getQuesName() {
		return quesName;
	}
	public void setQuesName(String quesName) {
		this.quesName = quesName;
	}
	public String getOptionContent() {
		return optionContent;
	}
	public void setOptionContent(String optionContent) {
		this.optionContent = optionContent;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}
