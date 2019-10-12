package com.dave.entity;

/**
 * 调查结果问题PO
 * 
 * @author Dave20190904
 *
 */
public class ResultQues {
	private Integer resultQuesId;
	private Integer resultId;
	private Integer quesNum;
	private Integer quesId;
	private String quesName;
	private String quesType;
	private String optionContent;
	
	public Integer getResultQuesId() {
		return resultQuesId;
	}
	public void setResultQuesId(Integer resultQuesId) {
		this.resultQuesId = resultQuesId;
	}
	public Integer getResultId() {
		return resultId;
	}
	public void setResultId(Integer resultId) {
		this.resultId = resultId;
	}
	public Integer getQuesNum() {
		return quesNum;
	}
	public void setQuesNum(Integer quesNum) {
		this.quesNum = quesNum;
	}
	public Integer getQuesId() {
		return quesId;
	}
	public void setQuesId(Integer quesId) {
		this.quesId = quesId;
	}
	public String getQuesName() {
		return quesName;
	}
	public void setQuesName(String quesName) {
		this.quesName = quesName;
	}
	public String getQuesType() {
		return quesType;
	}
	public void setQuesType(String quesType) {
		this.quesType = quesType;
	}
	public String getOptionContent() {
		return optionContent;
	}
	public void setOptionContent(String optionContent) {
		this.optionContent = optionContent;
	}
}
