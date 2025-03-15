DROP TABLE fault;

CREATE TABLE fault (
	agentid varchar(255) NOT NULL,
	m_w_faultid int8 NOT NULL,
	m_by_fault_board_type int4 NULL,
	m_by_fault_desc_value bytea NULL,
	m_by_fault_level int4 NULL,
	m_un_fault_occur_time int8 NULL,
	m_un_fault_renew_time int8 NULL,
	m_w_fault_type int8 NULL,
	m_w_sub_fault_type int4 NULL,
	CONSTRAINT fault_pkey PRIMARY KEY (agentid,m_w_faultid)
);

TRUNCATE "t_orgnization";