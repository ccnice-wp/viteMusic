package com.example.musicserver.common.net;

import com.alibaba.fastjson2.JSONArray;
import com.fasterxml.jackson.annotation.JsonAlias;

import java.util.HashMap;

/**
 * @Author CCNICE
 * @Date 2023/3/26
 */
public class ErrorMessage {

    HashMap<Object,Object> hashMap =  new HashMap<>();


    public ErrorMessage(String message) {
        hashMap.put("code", 200);
        hashMap.put("message", message);
        hashMap.put("success", false);
        hashMap.put("type", "error");
        hashMap.put("data", null);
    }

    public String getMessage() {
        return JSONArray.toJSONString(hashMap);
    }

}
