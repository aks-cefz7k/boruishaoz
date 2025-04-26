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
package com.openatc.agent.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
	/**
	 * compare日期大时，返回true
	 * */
	public static boolean compareDate(Date base, Date compare){
		
		if(compare.before(base))
			return false;
		return true;
	}

	
private static SimpleDateFormat sdfyyyyMMddhhmmss = new SimpleDateFormat("yyyyMMddHHmmss"); 
private static SimpleDateFormat dailyFormat = new SimpleDateFormat("yyyyMMdd"); 

private static SimpleDateFormat sdfNormal = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
private static SimpleDateFormat sdfES = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
	
	public static String date2str(Date date){
		return sdfNormal.format(date);		
	}
	
	public static String date2esstr(Date date){
		return sdfES.format(date);		
	}

	public static String date2strNormal(Date date){
		return sdfNormal.format(date);		
	}
	public static double getDateDiffInMinutes(Date d1,Date d2)
	{
		return Math.abs((d1.getTime()-d2.getTime()*1.0)/(1000*60));
	}
	public static Date stringToDate(String strDate)//"yyyy-MM-dd HH:mm:ss.sss"
	{
		try {
			return sdfNormal.parse(strDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static String longToString(Long timestamp){
		String format = sdfNormal.format(timestamp);
		return format;
	}

	public static String getDailyString(Date date){
		return dailyFormat.format(date);
	}
	
	public static Date getNDaysBefore(int nDays){
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		c.add(Calendar.DATE, - nDays);
		return c.getTime();
	}
	
	
	public static int getTimediffBetweenDates(Date date1, Date date2){
		int interval = (int) (date2.getTime() - date1.getTime())/(1000*60);
		return interval;
	}
	
	
	
}
