package com.example.musicserver.common.net;

import com.alibaba.fastjson2.JSONArray;

import java.util.HashMap;

/**
 * @Author CCNICE
 * @Date 2023/3/26
 */
public class FatalMessage {

    HashMap<Object,Object> hashMap =  new HashMap<>();


    public FatalMessage(String message) {
        hashMap.put("code", 500);
        hashMap.put("message", message);
        hashMap.put("success", false);
        hashMap.put("type", "error");
        hashMap.put("data", null);
    }

    public String getMessage() {
        return JSONArray.toJSONString(hashMap);
    }
}
