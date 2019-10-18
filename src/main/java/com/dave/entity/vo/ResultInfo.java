package com.dave.entity.vo;

import java.io.Serializable;

/**
 * 调查结果VO
 * 
 * @author Dave20190904
 *
 */
public class ResultInfo implements Serializable{
	private static final long serialVersionUID = 9006176143785726873L;
	private Integer resultId;
	private String mobileNum;
	private String cli;
	private String agentId;
	private Integer paperId;
	private String paperName;
	private String paperType;
	private String paperLanguage;
	private String inviteTime;
	private Integer[] quesNums;
	private Integer[] quesIds;
	private String[] quesNames;
	private String[] quesTypes;
	private String[] optionCons;

	public Integer getResultId() {
		return resultId;
	}

	public void setResultId(Integer resultId) {
		this.resultId = resultId;
	}

	public String getMobileNum() {
		return mobileNum;
	}

	public void setMobileNum(String mobileNum) {
		this.mobileNum = mobileNum;
	}

	public String getCli() {
		return cli;
	}

	public void setCli(String cli) {
		this.cli = cli;
	}

	public String getAgentId() {
		return agentId;
	}

	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}

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

	public String getInviteTime() {
		return inviteTime;
	}

	public void setInviteTime(String inviteTime) {
		this.inviteTime = inviteTime;
	}

	public Integer[] getQuesNums() {
		return quesNums;
	}

	public void setQuesNums(Integer[] quesNums) {
		this.quesNums = quesNums;
	}

	public Integer[] getQuesIds() {
		return quesIds;
	}

	public void setQuesIds(Integer[] quesIds) {
		this.quesIds = quesIds;
	}

	public String[] getQuesNames() {
		return quesNames;
	}

	public void setQuesNames(String[] quesNames) {
		this.quesNames = quesNames;
	}

	public String[] getQuesTypes() {
		return quesTypes;
	}

	public void setQuesTypes(String[] quesTypes) {
		this.quesTypes = quesTypes;
	}

	public String[] getOptionCons() {
		return optionCons;
	}

	public void setOptionCons(String[] optionCons) {
		this.optionCons = optionCons;
	}
}
