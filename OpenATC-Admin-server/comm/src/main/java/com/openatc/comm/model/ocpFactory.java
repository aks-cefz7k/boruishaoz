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
package com.openatc.comm.model;

import com.openatc.comm.common.CommunicationType;

import static com.openatc.comm.common.CommunicationType.*;

public class ocpFactory implements ProtocolFactory {
//    @Override
//    public CommunicationProxy createCommunication(CommunicationType communicationType) {
//        return new CommunicationProxy(communicationType);
//    }

    @Override
    public Communication createCommunication(Message message, CommunicationType commType, int exangeType) {
        if(commType == COMM_UDP_CONFIGER)
            return new UdpCommunicationForConfiger(message);
        else if (commType == COMM_UDP_HOSTPORT)
            return new UdpCommunicationStaticPort( OCP_PROTYPE, exangeType);
        else
            return null;
    }

    @Override
    public Message createMessage() {
        return new ocpMessage();
    }
}
