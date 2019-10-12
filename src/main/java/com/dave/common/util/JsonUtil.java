package com.dave.common.util;

/**
 * JSON工具类
 * 
 * @author Dave20190823
 *
 */
public class JsonUtil {
	
	/**
	 * 将String类型数据集转换为Int二维数组
	 * @param json
	 * @return
	 */
	public static Integer[][] jsonStrOnIntArray(String json) {
		Integer[][] array;
		String[] sFirst = json.split(";,");
		array = new Integer[sFirst.length][];
		for(int i=0; i<sFirst.length; i++) {
			String[] sSecond = sFirst[i].split(",");
			array[i] = new Integer[sSecond.length];
			for(int j=0; j<sSecond.length; j++) {
				array[i][j] = Integer.parseInt(sSecond[j]);
			}
		}
		return array;
	}
}
