/**
 * Copyright (c) 2020 kedacom
 * OpenATC is licensed under Mulan PSL v2.
 * You can use this software according to the terms and conditions of the Mulan PSL v2.
 * You may obtain a copy of Mulan PSL v2 at:
 * http://license.coscl.org.cn/MulanPSL2
 * THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND,
 * EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT,
 * MERCHANTABILITY OR FIT FOR A PARTICULAR PURPOSE.
 * See the Mulan PSL v2 for more details.
 **/
package com.openatc.core.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/** 
* @ClassName: DateUtil 
* @Description: TODO
* @author 
* @date 2019年10月19日 下午6:29:30 
*  
*/
public final class DateUtil {
	
	
private DateUtil() {
		
	}
	/**
	 * compare日期大时，返回true
	 * */
	/** 
	* @Title: compareDate 
	* @Description: TODO
	* @param base base
	* @param compare compare
	* @return boolean 
	* @throws 
	*/
	public static boolean compareDate(Date base, Date compare) {
		
		if (compare.before(base)) {
			return false;
		}			
		return true;
	}
	
	
private static final int MIN = 1000 * 60;
	
private static SimpleDateFormat sdfyyyyMMddhhmmss = new SimpleDateFormat("yyyyMMddHHmmss"); 
private static SimpleDateFormat dailyFormat = new SimpleDateFormat("yyyyMMdd"); 

private static SimpleDateFormat sdfNormal = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
private static SimpleDateFormat sdfES = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
	
	/** 
	* @Title: date2str 
	* @Description: TODO
	* @param date date
	* @return String 
	* @throws 
	*/
	public static String date2str(Date date) {
		return sdfNormal.format(date);		
	}
	
	/** 
	* @Title: date2esstr 
	* @Description: TODO
	* @param date date
	* @return String 
	* @throws 
	*/
	public static String date2esstr(Date date) {
		return sdfES.format(date);		
	}
	/** 
	* @Title: date2esstr 
	* @Description: TODO
	* @param date date
	* @return String 
	* @throws 
	*/
	public static String date2strNormal(Date date) {
		return sdfNormal.format(date);		
	}
	/** 
	* @Title: date2esstr 
	* @Description: TODO
	* @param d1 d1
	* @param d2 d2
	* @return double 
	* @throws 
	*/
	public static double getDateDiffInMinutes(Date d1, Date d2) {
		
		return Math.abs((d1.getTime() - d2.getTime() * 1.0) / MIN);
	}
	/** 
	* @Title: date2esstr 
	* @Description: TODO
	* @param strDate strDate
	* @return Date 
	* @throws 
	*/
	public static Date stringToDate(String strDate) {
		try {
			return sdfNormal.parse(strDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	/** 
	* @Title: date2esstr 
	* @Description: TODO
	* @param date date
	* @return String 
	* @throws 
	*/
	public static String getDailyString(Date date) {
		return dailyFormat.format(date);
	}
	/** 
	* @Title: date2esstr 
	* @Description: TODO
	* @param nDays nDays
	* @return Date 
	* @throws 
	*/
	public static Date getNDaysBefore(int nDays) {
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		c.add(Calendar.DATE, -nDays);
		return c.getTime();
	}
	
	/** 
	* @Title: date2esstr 
	* @Description: TODO
	* @param date1 date1
	* @param date2 date2
	* @return int 
	* @throws 
	*/
	public static int getTimediffBetweenDates(Date date1, Date date2) {
		int interval = (int) (date2.getTime() - date1.getTime()) / MIN;
		return interval;
		
	}
	
	
	
}
