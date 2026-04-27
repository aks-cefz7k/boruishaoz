package com.openatc.model.util;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ：zhangwenchao
 * @date ：Created in 2021/11/22 14:53
 * @description：集合类转换操作
 * @modified By：
 * @version: $
 */
public class MyArrayUtil {

    public static List<Integer> intArray2List(int[] ints){

        List<Integer> integerList  = new ArrayList<>();
        for(int i : ints){
            integerList.add(i);
        }

        return integerList;
    }

    public static int[] intList2Array( List<Integer> integerList){
        int[] ints = new int[integerList.size()];

        for(int i=0;i<integerList.size();i++){
            ints[i] = integerList.get(i);
        }

        return ints;
    }
}
