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

public enum CommunicationType {
    COMM_UDP_CONFIGER("udp"),
    COMM_UDP_HOSTPORT("udp-hp"),
    COMM_TCP("tcp");

    public static String OCP_PROTYPE  = "ocp";
    public static String SCP_PROTYPE  = "scp";

    public static String OPERATOER_TYPE_GET_REQUEST = "get-request";
    public static String OPERATOER_TYPE_GET_RESPONSE = "get-response";
    public static String OPERATOER_TYPE_SET_REQUEST = "set-request";
    public static String OPERATOER_TYPE_SET_RESPONSE = "set-response";
    public static String OPERATOER_TYPE_ERROR_REQUEST = "error-request";
    public static String OPERATOER_TYPE_ERROR_RESPONSE = "error-response";
    public static String OPERATOER_TYPE_REPORT = "report";

    public static String PLATFORM_TYPE_OPENATC = "OpenATC";

    public static int EXANGE_TYPE_DEVICE = 0;
    public static int EXANGE_TYPE_CENTER = 1;

    public static int COMM_SERVER_TYPE_CONFIGER = 0;
    public static int COMM_SERVER_TYPE_CENTER = 1;

    public static int COMM_SOCKET_TYPE_UDP = 0;
    public static int COMM_SOCKET_TYPE_TCP = 1;

    private String type;
    CommunicationType(String type) {
        this.type = type;
    }
    public String toString() {
        return type;
    }
}
