package com.openatc.agent.A1049.service;

import org.springframework.stereotype.Component;
import java.util.HashMap;
import java.util.Map;

@Component
public class InfotypeFactory {
    private static final Map<String, InfotypeProduct> prMap = new HashMap<>();

    public static synchronized InfotypeProduct createInfotypeProduct(String type) {
        InfotypeProduct infotypeProduct = null;
        if (prMap.containsKey(type)) {
            infotypeProduct = prMap.get(type);
        } else {
            if ("feature/detecter".equals(type)) {
                infotypeProduct = new DetParamProduct();
            }
            prMap.put(type, infotypeProduct);
        }
        return infotypeProduct;
    }
}
