package sj.hello_developer.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;

import org.json.JSONException;

import java.util.HashMap;

import sj.hello_developer.data.security.AESCrypt;

/**
 * SharedPreferences 抽象基类 ,所有的SharedPreferences实现类建议继承此类进行拓展
 * Created by sj on 10/22/16.
 */

public abstract class SharedPreferencesUtil {

    public static String getCommonSalt() {
        return "J_SALT";
    }

    protected static SharedPreferences.Editor getEditor(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext()).edit();
    }

    protected static SharedPreferences getSharedPreferences(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
    }

    /**
     * 存储加密的 sharedpreferences
     *
     * @param key
     * @param value
     */
    protected static void putSecurityString(Context context, String key, @Nullable String value) {
        String encryptStr = AESCrypt.encryptStr(getCommonSalt(), value);
        getEditor(context).putString(key, encryptStr).commit();
    }

    /**
     * 获取解密后的sharedpreferences，无需再次解密
     *
     * @param key
     * @return
     */
    protected static String getSecurityString(Context context, String key) {
        String encryptStr = getSharedPreferences(context).getString(key, "");
        return AESCrypt.decryptStr(getCommonSalt(), encryptStr);
    }

    /**
     * 删除
     * @param key
     */
    protected static void removeSecurityString(Context context, String key) {
        getEditor(context).remove(key).commit();
    }

    /**
     * map 转 json 加密并存储
     * @param context
     * @param key
     * @param developConfig
     * @return
     */
    protected static int putMapData(Context context, String key, HashMap<String, String> developConfig) {
        try {
            String json = CommonJSONParserHelper.mapToJson(developConfig);
            putSecurityString(context, key, json);
            return 0;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return -1;
    }
}
