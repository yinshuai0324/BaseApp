package com.company.app.utils.jumputils;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;


import com.company.app.R;
import com.company.app.global.Key;
import com.company.app.global.S;
import com.company.app.utils.AppManager;

import java.util.HashSet;
import java.util.Map;

/**
 * Created by yinshuai on 2017/12/16.
 */
public class JumpActivity implements Key {


    public static JumpPara jumpKV = new JumpPara();


    public static JumpPara fastJumpPara = new JumpPara();

    public static HashSet<String> fastHashSet = new HashSet<>();


    public static void jump(Activity activity, String toActivityName) {
        jump(activity, toActivityName, null, -1, false);
    }

    public static void jump(Activity activity, String toActivityName, boolean isKill) {
        jump(activity, toActivityName, null, -1, isKill);
    }

    public static void jump(Activity activity, String toActivityName, int resultCode) {
        jump(activity, toActivityName, null, resultCode, false);
    }

    public static void jump(Activity activity, String toActivityName, JumpPara map, boolean isKill) {
        jump(activity, toActivityName, map, -1, isKill);
    }

    public static void jump(Activity activity, String toActivityName, JumpPara map) {
        jump(activity, toActivityName, map, -1);
    }

    public static void jump(Activity activity, String toActivityName, JumpPara map, int resultCode) {
        jump(activity, toActivityName, map, resultCode, false);
    }

    public static void jump(Activity activity, String toActivityName, JumpPara map, int resultCode, boolean isKillOld) {
        if (TextUtils.isEmpty(toActivityName)) {
            return;
        }
        Intent intent = new Intent();
        intent.setAction(toActivityName);
        if (fastHashSet.contains(toActivityName)) {
            fastJumpPara.put(toActivityName, map);
        } else {
            if (map != null) {
                intent.putExtra("_map", map);
                for (Object o : map.entrySet()) {
                    Map.Entry entry = (Map.Entry) o;
                    intent.putExtra((String) entry.getKey(), entry.getValue() == null ? null : entry.getValue().toString());
                }
            }
        }
        if (resultCode > 0) {
            activity.startActivityForResult(intent, resultCode);
        } else {
            activity.startActivity(intent);
        }
//        activity.overridePendingTransition(R.anim.slide_out_left, R.anim.slide_in_right);
        if (isKillOld) {
            activity.finish();
        }
    }


    public static void jumpMain(int page) {
        jumpMain(page, false);
    }

    /**
     * 跳转到首页
     *
     * @param page
     */
    public static void jumpMain(int page, boolean isRefresh) {
        S.setI(MAIN_TAG_KEY, page);
        S.setB(MAIN_TAG_REFRESH, isRefresh);
        AppManager.getAppManager().finishOtherActivity();
    }
}
