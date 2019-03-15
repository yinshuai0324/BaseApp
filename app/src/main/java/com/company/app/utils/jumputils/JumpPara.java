package com.company.app.utils.jumputils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by yinshuai on 16-1-15.
 */
public class JumpPara extends HashMap<String, Object> {

    public JumpPara() {
        super();
    }

    public JumpPara(Map<String, Object> hashMap) {
        for (Object o : hashMap.entrySet()) {
            Entry entry = (Entry) o;
            put((String) entry.getKey(), entry.getValue());
        }
    }
}