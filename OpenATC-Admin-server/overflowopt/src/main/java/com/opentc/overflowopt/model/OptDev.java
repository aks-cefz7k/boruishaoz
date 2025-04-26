package com.opentc.overflowopt.model;


import lombok.Data;

@Data
public class OptDev {


    private     String              agentid;
    private     String              user;
    private     String              source;
    private     String              operation;
    private     String              infotype;
    private     OptProgram          data;


}
