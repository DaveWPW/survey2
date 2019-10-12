package com.dave.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.dave.entity.Paper;

/**
 * Paper持久层接口
 * 
 * @author Dave20190828
 *
 */
public interface PaperDao {
	
	int checkoutPaperName(@Param("paperName") String paperName, @Param("paperLanguage") String paperLanguage, @Param("paperId") Integer paperId);
	
	int addPaper(Paper paper);
	
//	@Select("select seq_paper_id.nextval from dual")
//	int getPaperId();
	
	@Select("select count(*) from su_paper where status != 0")
	int getAllPaperCount();
	
	List<Paper> findPaperList(
            @Param("startIndex") int startIndex, @Param("pageSize") int pageSize, @Param("paperName") String paperName);
	
	@Update("update su_paper set status = 0, modify_time = now() where paper_id = #{paperId}")
	int deletePaper(@Param("paperId") int paperId);
	
	@Update("update su_paper set status = #{status} where paper_id = #{paperId}")
	int updateStatus(Paper paper);
		
	@Select("select * from su_paper where paper_id = #{paperId} and status != 0")
	Paper selectPaperById(@Param("paperId") int paperId);
	
	int updatePaper(Paper paper);
	
	@Select("select * from su_paper where paper_name=#{paperName} and paper_language=#{paperLanguage} and status = 1")
	Paper findStartPaper(@Param("paperName") String paperName, @Param("paperLanguage") String paperLanguage);
}
