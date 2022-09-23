package com.openatc.comm.data;

import com.google.gson.JsonElement;
import lombok.Data;
import org.springframework.stereotype.Component;




@Data
@Component
public class ThirdPlatMessageData {

    String platform;
    String type;
    JsonElement data;


    public ThirdPlatMessageData() {

    }
}
