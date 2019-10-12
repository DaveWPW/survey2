package com.dave.entity.vo;

/**
 * 调查问题VO
 * 
 * @author Dave20190826
 *
 */
public class QuesInfo {
	/**问题ID*/
	private Integer quesId; 
	/**问题名*/
	private String quesName;
	/**问题类型*/
	private String quesType;
	/**问题作答*/
	private Integer must;
	/**选项ID数组*/
	private Integer[] optionIds;
	/**选项内容数组*/
	private String[] options;
	/**是否可填空标识数组*/
	private Integer[] flags;
	
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
	public Integer getMust() {
		return must;
	}
	public void setMust(Integer must) {
		this.must = must;
	}
	public Integer[] getOptionIds() {
		return optionIds;
	}
	public void setOptionIds(Integer[] optionIds) {
		this.optionIds = optionIds;
	}
	public String[] getOptions() {
		return options;
	}
	public void setOptions(String[] options) {
		this.options = options;
	}
	public Integer[] getFlags() {
		return flags;
	}
	public void setFlags(Integer[] flags) {
		this.flags = flags;
	}
}
