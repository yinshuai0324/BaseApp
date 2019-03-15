package com.company.app.network.entity;

import com.alibaba.fastjson.JSON;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yinshuai
 * @date 2018/4/18
 */

public class RequestParams {

    private Map<String, String> hashMap;

    public RequestParams() {
        hashMap = new HashMap<>();
    }

    public RequestParams addParams(String key, Object value) {
        if (hashMap != null) {
            if (value instanceof List) {
                String reslut = String.join(",", (List) value);
                hashMap.put(key, reslut);
            } else {
                hashMap.put(key, value.toString());
            }
        }
        return this;
    }

    public RequestParams removeParams(String key) {
        if (hashMap != null) {
            hashMap.remove(key);
        }
        return this;
    }

    public String toJson() {
        if (hashMap != null) {
            return JSON.toJSONString(hashMap);
        }
        return "{}";
    }

    public Map<String, String> getValue() {
        if (hashMap == null) {
            return new HashMap<>();
        } else {
            return hashMap;
        }

    }


    public JSONObject getJsonObj() {
        try {
            return new JSONObject(JSON.toJSONString(hashMap));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return new JSONObject();
    }
}
