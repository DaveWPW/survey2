package com.dave.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.dave.entity.PaperQuesOption;

public interface PaperQuesOptionDao {
	
	int addPaperQuesOption(PaperQuesOption paperQuesOption);
	
	@Delete("delete from su_paper_ques_option where paper_id = #{paperId}")
	int deletePaperQuesOption(@Param("paperId") int paperId);
	
	@Select("select * from su_paper_ques_option where paper_id = #{paperId}")
	List<PaperQuesOption> getPaperQuesOption(@Param("paperId") int paperId);
	
}
