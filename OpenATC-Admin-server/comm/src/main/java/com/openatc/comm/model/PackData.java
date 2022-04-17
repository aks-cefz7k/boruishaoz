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

import lombok.Data;

@Data
public class PackData {
    private byte[] m_packData;
    private int m_packDataSize;

    public PackData(byte[] m_packData, int m_packDataSize) {
        this.m_packData = new byte[m_packDataSize];
        for(int i=0; i<m_packDataSize; i++){
            this.m_packData[i] = m_packData[i];
        }
        this.m_packDataSize = m_packDataSize;
    }

    public PackData() {
    }

    public byte[] getM_packData() {
        return m_packData;
    }

    public void setM_packData(byte[] m_packData) {
        this.m_packData = m_packData;
    }

    public void setValue(int nValue)
    {
        m_packDataSize =nValue;
    }
}
