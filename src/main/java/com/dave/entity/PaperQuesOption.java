package com.dave.entity;

import java.io.Serializable;

/**
 * 问卷问题选项PO
 * 
 * @author Dave20190918
 *
 */
public class PaperQuesOption implements Serializable{
	private static final long serialVersionUID = -9171298449617934280L;
	/**问卷问题选项ID*/
	private Integer paperQuesOptionId;
	/**问卷ID*/
	private Integer paperId;
	/**问题ID*/
	private Integer quesId;
	/**问题类型*/
	private String quesType;
	/**选项ID*/
	private Integer optionId;
	/**下一题Num*/
	private Integer selectNum;
	
	public Integer getPaperQuesOptionId() {
		return paperQuesOptionId;
	}
	public void setPaperQuesOptionId(Integer paperQuesOptionId) {
		this.paperQuesOptionId = paperQuesOptionId;
	}
	public Integer getPaperId() {
		return paperId;
	}
	public void setPaperId(Integer paperId) {
		this.paperId = paperId;
	}
	public Integer getQuesId() {
		return quesId;
	}
	public void setQuesId(Integer quesId) {
		this.quesId = quesId;
	}
	public String getQuesType() {
		return quesType;
	}
	public void setQuesType(String quesType) {
		this.quesType = quesType;
	}
	public Integer getOptionId() {
		return optionId;
	}
	public void setOptionId(Integer optionId) {
		this.optionId = optionId;
	}
	public Integer getSelectNum() {
		return selectNum;
	}
	public void setSelectNum(Integer selectNum) {
		this.selectNum = selectNum;
	}
}
