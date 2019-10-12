package com.dave.service;

import org.springframework.web.multipart.MultipartFile;

import com.dave.common.vo.PageObject;
import com.dave.entity.Ques;
import com.dave.entity.vo.QuesInfo;

/**
 * Ques业务层接口
 * 
 * @author Dave20190826
 *
 */
public interface QuesService {
	
	PageObject<Ques> findQuesList(int pageCurrent, String quesName);
	
	int addQues(QuesInfo quesInfo);
	
	String deleteQues(Integer... quesIds);
	
	QuesInfo getQuesOption(int quesId);
	
	String updateQues(QuesInfo quesInfo);
	
	String importQues(String fileName, MultipartFile file);
}
