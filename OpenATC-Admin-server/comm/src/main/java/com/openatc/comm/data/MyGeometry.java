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
package com.openatc.comm.data;

import lombok.Data;

import java.util.Arrays;

@Data
public class MyGeometry {
    String type;
    double[] coordinates;

    public double[] getCoordinates() {
        return coordinates.clone();
    }

    public void setCoordinates(double[] coordinates) {
        this.coordinates = coordinates.clone();
    }

    @Override
    public String toString(){
        String strTemp = String.format("{\"type\":\"%s\",\"coordinates\":%s}",type,Arrays.toString(coordinates) );

        return  strTemp;
    }
}
