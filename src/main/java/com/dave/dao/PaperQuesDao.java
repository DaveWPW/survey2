package com.dave.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.dave.entity.PaperQues;

/**
 * PaperQues持久层接口
 * 
 * @author Dave20190829
 *
 */
public interface PaperQuesDao {
	
	int addPaperQues(PaperQues paperAll);
	
	@Delete("delete from su_paper_ques where paper_id = #{paperId}")
	int deletePaperQues(@Param("paperId") int paperId);
	
	@Select("select * from su_paper_ques where paper_id = #{paperId} order by ques_num asc")
	List<PaperQues> selectQuesByPaperId(@Param("paperId") int paperId);
	
}
