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

import java.util.UUID;

/** 
* @ClassName: UUIDUtil 
* @Description:
* @author 
* @date 2019年10月19日 下午6:47:09 
*  
*/
public final class UUIDUtil {
	
	private static final int INDEX1 = 8;
	private static final int INDEX2 = 9;
	private static final int INDEX3 = 13;
	private static final int INDEX4 = 14;
	private static final int INDEX5 = 18;
	private static final int INDEX6 = 19;	
	private static final int INDEX7 = 23;
	private static final int INDEX8 = 24;
	
	
	private UUIDUtil() {
		
	}
    /** 
    * @Title: getUUID 
    * @Description:
    * @return String 
    * @throws 
    */
    public static String getUUID() { 
        String s = UUID.randomUUID().toString(); 
        //去掉“-”符号 
        return s.substring(0, INDEX1) + s.substring(INDEX2, INDEX3) + s.substring(INDEX4, INDEX5) + s.substring(INDEX6, INDEX7) + s.substring(INDEX8); 
    } 
}
