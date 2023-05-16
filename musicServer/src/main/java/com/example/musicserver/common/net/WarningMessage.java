package com.example.musicserver.common.net;

import com.alibaba.fastjson2.JSONArray;

import java.util.HashMap;

/**
 * @Author CCNICE
 * @Date 2023/3/26
 */
public class WarningMessage {

    HashMap<Object,Object> hashMap =  new HashMap<>();


    public WarningMessage(String message) {
        hashMap.put("code", 200);
        hashMap.put("message", message);
        hashMap.put("success", false);
        hashMap.put("type", "warning");
        hashMap.put("data", null);
    }

    public String getMessage() {
        return JSONArray.toJSONString(hashMap);
    }
}
