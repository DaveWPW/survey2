package com.dave.entity;

import java.io.Serializable;

/**
 * 调查问题选项PO
 * 
 * @author Dave20190827
 *
 */
public class QuesOption implements Serializable{
	private static final long serialVersionUID = 2401852723468144332L;
	/**选项ID*/
	private int optionId;
	/**对应的问题ID*/
	private int quesId;
	/**选项内容*/
	private String optionContent;
	/**是否可填空 0：否，1：是*/
	private int flag;
	/**问题状态 0：删除，1：使用*/
	private int status;
	
	public int getOptionId() {
		return optionId;
	}
	public void setOptionId(int optionId) {
		this.optionId = optionId;
	}
	public int getQuesId() {
		return quesId;
	}
	public void setQuesId(int quesId) {
		this.quesId = quesId;
	}
	public String getOptionContent() {
		return optionContent;
	}
	public void setOptionContent(String optionContent) {
		this.optionContent = optionContent;
	}
	public int getFlag() {
		return flag;
	}
	public void setFlag(int flag) {
		this.flag = flag;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
}