package com.company.app.network.entity;

import com.alibaba.fastjson.JSON;

import java.util.HashMap;
import java.util.Map;

/**
 * @author yinshuai
 * @date 2018/4/18
 */

public class RequestObjectParams {

    private Map<String, Object> hashMap;

    public RequestObjectParams() {
        hashMap = new HashMap<>();
    }

    public RequestObjectParams addParams(String key, Object value) {
        if (hashMap != null) {
            hashMap.put(key, value);
        }
        return this;
    }

    public RequestObjectParams removeParams(String key) {
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

    public Map<String, Object> getValue() {
        if (hashMap == null) {
            return new HashMap<>();
        } else {
            return hashMap;
        }

    }
}
