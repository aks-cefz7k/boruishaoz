package com.openatc.model.model;


import lombok.Data;

import java.util.List;

@Data
public class Phase {
    private   Integer           id;
    private   List<Integer>     direction;
    private   List<Integer>     peddirection;
    private   Integer           controltype;
    private   Integer           mingreen;
    private   Integer           max1;
    private   Integer           max2;
    private   Integer           passage;
    private   Integer           phasewalk;
    private   Integer           pedclear;
    private   Integer           yellow;
    private   Integer           redclear;
    private   Integer           flashgreen;
    private   Integer           redpulse;
    private   Integer           greenpulse;
    private   Integer           ring;
    private   List<Integer>     concurrent;

}
