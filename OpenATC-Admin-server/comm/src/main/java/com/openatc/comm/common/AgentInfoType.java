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
package com.openatc.comm.common;




/** 
* @ClassName: AgentInfoType 
* @Description: TODO
* @author zs
* @date 2019年10月19日 下午6:19:12 
*  
*/
public enum AgentInfoType {
	
	
	Status_Pattern("status/pattern"),
	device_DeviceLogin("device/login"),
	heartbeat("heartbeat"),
	Status_Plan("status/plan"),
	Agent_Split("optplan/pattern"),
	Agent_RealTimePlan("status/realtimeplan");
	
	
	
	private String infotype;
	AgentInfoType(String infotype) {
		this.infotype = infotype;
	}
	/** 
	* @Title: toString 
	* @Description: TODO
	* @return String
	* @throws 
	*/
	public String toString() {
		return infotype;
	}

}
