package com.dave.dao;

import java.util.List;

//import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.dave.entity.Result;
import com.dave.entity.vo.ResultExportInfo;

/**
 * Result持久层接口
 * 
 * @author Dave20190904
 *
 */
public interface ResultDao {
	
	int addResult(Result result);
	
//	@Select("select seq_result_id.nextval from dual")
//	int getResultId();
	
	@Select("select count(*) from su_result where status = 1")
	int getAllResultCount();
	
	List<Result> findResultList(
            @Param("startIndex") int startIndex, @Param("pageSize") int pageSize,
            @Param("paperName") String paperName, @Param("startDate") String startDate,
            @Param("endDate") String endDate);
	
//	@Delete("delete from su_result where result_id = #{resultId}")
	@Update("update su_result set status = 0, modify_time = now() where result_id = #{resultId}")
	int deleteResult(@Param("resultId") int resultId);
	
	List<ResultExportInfo> exportResult(
            @Param("paperName") String paperName, @Param("startDate") String startDate,
            @Param("endDate") String endDate);
	
}
