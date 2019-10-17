package com.dave.entity.vo;

import java.io.Serializable;

/**
 * 调查问卷VO
 * 
 * @author davewpw
 *
 */
public class PaperInfo implements Serializable{
	private static final long serialVersionUID = -8142497725375448079L;
	/**问卷ID*/
	private Integer paperId;
	/**问卷名称*/
	private String paperName;
	/**问卷类型*/
	private String paperType;
	/**语言种型*/
	private String paperLanguage;
	/**问题Num*/
	private Integer[] quesNum;
	/**问题IDs*/
	private Integer[] quesIds;
	/**问题类型*/
	private String[] quesTypes;
	private String quesOptionStr;
	private String selectQuesStr;
	/**问题选项IDs, 用于分支问卷*/
	private Integer[][] quesOption;
	/**问题选项选择下一题的题序, 用于分支问卷*/
	private Integer[][] selectQues;
	/**分支作答总题数，用于分支问卷*/
	private Integer quesSum;
	/**问卷标题*/
	private String paperTitle;
	/**欢迎语句*/
	private String greet;
	/**感谢语句*/
	private String thank;
	
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
	public Integer[] getQuesNum() {
		return quesNum;
	}
	public void setQuesNum(Integer[] quesNum) {
		this.quesNum = quesNum;
	}
	public Integer[] getQuesIds() {
		return quesIds;
	}
	public void setQuesIds(Integer[] quesIds) {
		this.quesIds = quesIds;
	}
	public String[] getQuesTypes() {
		return quesTypes;
	}
	public void setQuesTypes(String[] quesTypes) {
		this.quesTypes = quesTypes;
	}
	public String getQuesOptionStr() {
		return quesOptionStr;
	}
	public void setQuesOptionStr(String quesOptionStr) {
		this.quesOptionStr = quesOptionStr;
	}
	public String getSelectQuesStr() {
		return selectQuesStr;
	}
	public void setSelectQuesStr(String selectQuesStr) {
		this.selectQuesStr = selectQuesStr;
	}
	public Integer[][] getQuesOption() {
		return quesOption;
	}
	public void setQuesOption(Integer[][] quesOption) {
		this.quesOption = quesOption;
	}
	public Integer[][] getSelectQues() {
		return selectQues;
	}
	public void setSelectQues(Integer[][] selectQues) {
		this.selectQues = selectQues;
	}
	public Integer getQuesSum() {
		return quesSum;
	}
	public void setQuesSum(Integer quesSum) {
		this.quesSum = quesSum;
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
}
