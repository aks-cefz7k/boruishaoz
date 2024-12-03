package com.openatc.comm.common;

import java.io.*;
import java.util.Properties;
import java.util.logging.Logger;

public class PropertiesUtil {
    private static Properties prop = new Properties();

    private static Logger logger = Logger.getLogger(PropertiesUtil.class.toString());

    static {
        try {
            InputStream in = PropertiesUtil.class.getResourceAsStream("/application.properties");
            BufferedReader bf = new BufferedReader(new InputStreamReader(in));
            prop.load(bf);

            //从当前目录下读取配置文件信息，覆盖资源目录的参数
            FileReader fr = new FileReader("./application.properties");
            BufferedReader bf2 = new BufferedReader(fr);
            prop.load(bf2);


        }catch (IOException e){
            logger.warning("PropertiesUtil Exception: " + e.getMessage());
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
