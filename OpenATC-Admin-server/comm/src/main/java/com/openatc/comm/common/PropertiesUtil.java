package com.openatc.comm.common;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;
public class PropertiesUtil {
    private static Properties prop = new Properties();

    static {
        try {
            InputStream in = PropertiesUtil.class.getResourceAsStream("/application.properties");
            BufferedReader bf = new BufferedReader(new InputStreamReader(in));
            prop.load(bf);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * 根据key读取对应的value
     * @param key
     * @return
     */
    public static String getStringProperty(String key){
        return prop.getProperty(key);
    }

    public static int getIntProperty(String key){
        return Integer.parseInt(prop.getProperty(key));
    }
}
