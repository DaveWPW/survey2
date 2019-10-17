package com.dave.entity;

import java.io.Serializable;

/**
 * 调查问卷问题PO
 * 
 * @author Dave20190829
 *
 */
public class PaperQues implements Serializable{
	private static final long serialVersionUID = 6558278226117544285L;
	/**问卷问题ID*/
	private Integer paperQuesId;
	/**问卷ID*/
	private Integer paperId;
	/**问题序号*/
	private Integer quesNum;
	/**问题ID*/
	private Integer quesId;
	
	public Integer getPaperQuesId() {
		return paperQuesId;
	}
	public void setPaperQuesId(Integer paperQuesId) {
		this.paperQuesId = paperQuesId;
	}
	public Integer getPaperId() {
		return paperId;
	}
	public void setPaperId(Integer paperId) {
		this.paperId = paperId;
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
}
