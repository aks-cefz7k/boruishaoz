<<<<<<< HEAD
//package com.openatc.agent.controller;
//
//
//
//
//import com.google.gson.Gson;
//import com.google.gson.JsonObject;
//import com.openatc.comm.common.CommClient;
//import com.openatc.comm.data.MessageData;
////import com.openatc.comm.data.ThirdPlatMessageData;
//import com.openatc.core.model.RESTRet;
//import com.openatc.core.util.RESTRetUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.io.UnsupportedEncodingException;
//import java.net.SocketException;
//import java.text.ParseException;
//
//
//
//@RestController
//@CrossOrigin
//public class ThirdPlatMessageController {
//
//
//    @Autowired
//    private CommClient commClient;
//
//
//    @Value("${agent.thirdplat.ip}")
//    private String adapterIP;
//
//    @Value("${agent.thirdplat.port}")
//    private Integer adapterPort;
//
//    private String protocolType = "scp";
//
//
////    @PostMapping(value = "/centeradapter/custom")
////    public RESTRet postDevsMessage(@RequestBody JsonObject jsonObject) throws SocketException, ParseException, UnsupportedEncodingException {
////
////        //当收到第三方的post请求时，直接将该请求发送给centeradapter python脚本
////        Gson gson = new Gson();
////        ThirdPlatMessageData message = gson.fromJson(jsonObject, ThirdPlatMessageData.class);
////        MessageData responceData = null;
////
////        try {
////            responceData = commClient
////                    .exangeThirdPlatMessage(adapterIP, adapterPort, protocolType, message);
////        } catch (Exception e) {
////            e.printStackTrace();
////        }
////
////        System.out.println("responsdata = " + responceData);
////        return RESTRetUtils.successObj(responceData);
////    }
//
//
//}
=======
package com.openatc.agent.controller;




import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.openatc.comm.common.CommClient;
import com.openatc.comm.data.MessageData;
import com.openatc.comm.data.ThirdPlatMessageData;
import com.openatc.core.model.RESTRet;
import com.openatc.core.util.RESTRetUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.net.SocketException;
import java.text.ParseException;



@RestController
@CrossOrigin
public class ThirdPlatMessageController {


    @Autowired
    private CommClient commClient;

    @Value("${agent.thirdplat.ip}")
    private String adapterIP;

    @Value("${agent.thirdplat.port}")
    private Integer adapterPort;

    private String protocolType = "scp";


    @PostMapping(value = "/openatc/centeradatpter/custom")
    public RESTRet postDevsMessage(@RequestBody JsonObject jsonObject) throws SocketException, ParseException, UnsupportedEncodingException {

        //当收到第三方的post请求时，直接将该请求发送给centeradapter python脚本
        Gson gson = new Gson();
        ThirdPlatMessageData message = gson.fromJson(jsonObject, ThirdPlatMessageData.class);
        MessageData responceData = null;

        try {
            responceData = commClient
                    .exangeThirdPlatMessage(adapterIP, adapterPort, protocolType, message);
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("responsdata = " + responceData);

        return RESTRetUtils.successObj(responceData);
    }


}
>>>>>>> e11a0a4de3379285c554d2439b732bd0d2dfa34a
