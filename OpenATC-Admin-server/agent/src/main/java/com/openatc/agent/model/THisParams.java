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
package com.openatc.agent.model;

import lombok.Data;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

/** 
* @ClassName: TParams 
* @Description: 历史操作记录表
* @author 
* @date 2019年10月19日 下午5:54:16 
*  
*/
@Data
@Entity
@Table(name = "operation_record")
public class THisParams {
	@Id
	@GeneratedValue
	private int id;

	@Column
	private String operator; // 操作者

	@UpdateTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column
	private Date opertime; // 操作时间

	@Column
	private String source;   // 源地址

	@Column
	private String agentid;  // 设备ID

	@Column
	private String infotype; //消息类型

	@Column
	private String status;// 返回状态

	@Column(columnDefinition="TEXT")
	private String requestbody;    // 操作请求内容

	@Column(columnDefinition="TEXT")
	private String responsebody;   // 操作返回内容

	@Column
	private String extend_01;  // 预留

	@Column
	private String extend_02;

	@Column
	private Integer subInfoType; //消息子类型（控制类型）

	@Column
	private String responseCode; //请求错误码

	@Column
	private Integer deviceErrorCode; //特征参数错误码

	public THisParams(int id, String operator, Date opertime, String source, String agentid, String infotype, String status, Integer subInfoType, String responseCode,Integer deviceErrorCode) {
		this.id = id;
		this.operator = operator;
		this.opertime = (Date)opertime.clone();
		this.source = source;
		this.agentid = agentid;
		this.infotype = infotype;
		this.status = status;
		this.subInfoType = subInfoType;
		this.responseCode = responseCode;
		this.deviceErrorCode = deviceErrorCode;
	}

	public THisParams() {
	}

}
