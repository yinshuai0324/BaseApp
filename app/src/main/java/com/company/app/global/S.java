package com.company.app.global;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Base64;

import com.orhanobut.logger.Logger;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;


/**
 * Created by yinshuai on 2018/4/22.
 */

public class S {
    private static Context mContext;
    private static SharedPreferences preferences;
    private static SharedPreferences.Editor editor;
    private static String SHARED_KEY = "BASE_APP";

    public static void init(Context context) {
        mContext = context;
        preferences = context.getSharedPreferences(SHARED_KEY, 0);
        editor = preferences.edit();
    }

    public static void setB(String key, boolean value) {
        editor.putBoolean(key, value);
        editor.commit();
    }

    public static void setS(String key, String value) {
        editor.putString(key, value);
        editor.commit();
    }

    public static void setI(String key, int value) {
        editor.putInt(key, value);
        editor.commit();
    }

    public static void setF(String key, float value) {
        editor.putFloat(key, value);
        editor.commit();
    }

    public static void setObj(String key, Object value) {
        if (value != null) {
            if (value instanceof Serializable) {
                try {
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    ObjectOutputStream oos = new ObjectOutputStream(baos);
                    //把对象写到流里
                    oos.writeObject(value);
                    String temp = new String(Base64.encode(baos.toByteArray(), Base64.DEFAULT));
                    editor.putString(key, temp);
                    editor.commit();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            } else {
                Logger.w(value.getClass().getSimpleName() + "没有序列化 请序列化...");
            }
        }

    }

    public static String getS(String key) {
        return preferences.getString(key, "");
    }

    public static int getI(String key) {
        return preferences.getInt(key, -1);
    }

    public static boolean getB(String key, boolean defValue) {
        return preferences.getBoolean(key, defValue);
    }

    public static float getF(String key) {
        return preferences.getFloat(key, -1);
    }

    public static Object getObj(String key) {
        String temp = getS(key);
        ByteArrayInputStream bais = new ByteArrayInputStream(Base64.decode(temp.getBytes(), Base64.DEFAULT));
        Object o = null;
        try {
            ObjectInputStream ois = new ObjectInputStream(bais);
            o = ois.readObject();
        } catch (EOFException e) {
            Logger.i("用户未登陆");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return o;

    }

    public static void remove(String key) {
        editor.remove(key);
        editor.commit();
    }

}
