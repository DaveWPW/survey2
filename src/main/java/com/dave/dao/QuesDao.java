package com.dave.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.dave.entity.Ques;

/**
 * Ques持久层接口
 * 
 * @author Dave20190826
 *
 */
public interface QuesDao {
	
	@Select("select count(*) from su_ques where status=1")
	int getAllQuesCount();
	
	List<Ques> findQuesList(
            @Param("startIndex") int startIndex, @Param("pageSize") int pageSize, @Param("quesName") String quesName);
	
	int addQues(Ques ques);
	
//	@Select("select seq_ques_id.nextval from dual")
//	int getQuesId();
	
	int deleteQues(@Param("quesId") int quesId);
	
	@Select("select * from su_ques where ques_id = #{quesId} and status = 1")
	Ques selectQuesById(@Param("quesId") int quesId);
	
	List<String> findUsePaperNameByQuesIds(@Param("quesIds") Integer... quesIds);
	
	List<String> findUseQuesIdByQuesIds(@Param("quesIds") Integer... quesIds);
	
	int updateQues(Ques ques);
	
}
