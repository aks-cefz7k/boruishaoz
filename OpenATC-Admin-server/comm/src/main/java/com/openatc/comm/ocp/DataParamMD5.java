package com.openatc.comm.ocp;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @ClassName: DataParamMD5
 * @Description: TODO MD5加密相关处理类
 * @author  liangting
 * @date 2020年5月30日 上午10:16:06
 *
 */
public class DataParamMD5 {

    public static String getMD5(String datastr1) throws UnsupportedEncodingException {
        String datastr = null;
        if (datastr1 != null) {
            char stchar = '"';
            char stchar1 =' ';
            char stchar2= '\0';
            char stchar3= '\t';
            char stchar4= '\r';
            char stchar5= '\n';
            char stchar6= '\b';
            StringBuffer stringBuffer = new StringBuffer("");
            for (int i = 0; i < datastr1.length(); i++) {
                if (datastr1.charAt(i) != stchar&&datastr1.charAt(i) != stchar1&&datastr1.charAt(i) != stchar2&&datastr1.charAt(i) != stchar3&&datastr1.charAt(i) != stchar4&&datastr1.charAt(i) != stchar5&&datastr1.charAt(i) != stchar6) {
                    stringBuffer.append(datastr1.charAt(i));
                }
            }
            datastr = stringBuffer.toString();
        }

//        String md5 = DigestUtils.md5DigestAsHex(str.getBytes("UTF-8"));
        byte[] secretBytes = null;
        try {
            secretBytes = MessageDigest.getInstance("md5").digest(
                    datastr.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("没有这个md5算法！");
        }
        String md5code = new BigInteger(1, secretBytes).toString(16);
        for (int i = 0; i < 32 - md5code.length(); i++) {
            md5code = "0" + md5code;
        }
        return md5code;
    }
    /**
     * @param  str
     * @return UTF-8编码的字符串
     * @Title: ReadDataSchedule
     * @Description: 字符串UTF8编码
     */
    public static String toUTF8(String str) {
        if (isEmpty(str)) {
            return "";
        }
        try {
            if (str.equals(new String(str.getBytes("GB2312"), "GB2312"))) {
                str = new String(str.getBytes("GB2312"), "utf-8");
                return str;
            }
        } catch (Exception exception) {
        }
        try {
            if (str.equals(new String(str.getBytes("ISO-8859-1"), "ISO-8859-1"))) {
                str = new String(str.getBytes("ISO-8859-1"), "utf-8");
                return str;
            }
        } catch (Exception exception1) {
        }
        try {
            if (str.equals(new String(str.getBytes("GBK"), "GBK"))) {
                str = new String(str.getBytes("GBK"), "utf-8");
                return str;
            }
        } catch (Exception exception3) {
        }
        return str;
    }

    /**
     * @param  str
     * @return
     * @Title: isEmpty
     * @Description: 判断字符是否为空
     */
    public static boolean isEmpty(String str) {
        // 如果字符串不为null，去除空格后值不与空字符串相等的话，证明字符串有实质性的内容
        if (str != null && !str.trim().isEmpty()) {
            return false;// 不为空
        }
        return true;// 为空
    }

    public static String getUTF8XMLString(String xml) {
        // A StringBuffer Object
        StringBuffer sb = new StringBuffer();
        sb.append(xml);
        String xmString = "";
        String xmlUTF8="";
        try {
            xmString = new String(sb.toString().getBytes("UTF-8"),"UTF-8");
            xmlUTF8 = URLEncoder.encode(xmString, "UTF-8");
            //System.out.println("utf-8 编码：" + xmlUTF8) ;
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        // return to String Formed
        return xmlUTF8;
    }
}
