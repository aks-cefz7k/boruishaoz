package com.openatc.agent.config;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotatedTypeMetadata;

public class RedisSubEnable implements Condition {
    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        Environment environment = context.getEnvironment();
        String enable = environment.getProperty("spring.redissub.enable");
        if ("true".equals(enable)){
            return true;
        }else{
            return false;
        }
    }
}
