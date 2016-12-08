package sj.hello_developer.data;

import android.content.Context;
import android.text.TextUtils;

import org.json.JSONException;

import java.util.HashMap;

/**
 * Created by sj on 01/12/2016.
 */

public class DeveloperConfigManage extends SharedPreferencesUtil{

    public static String DEVELOP_CONFIG_SET = "DEVELOP_CONFIG_SET";

    private HashMap<String, String> developConfig;

    private static DeveloperConfigManage sInstance;

    private DeveloperConfigManage() { }

    public synchronized static DeveloperConfigManage getInstance() {
        if (sInstance == null) {
            sInstance = new DeveloperConfigManage();
        }
        return sInstance;
    }

    protected HashMap<String, String> getConfigMap(Context context) throws JSONException {
        if(developConfig != null) {
           return developConfig;
        }

        String json = SharedPreferencesUtil.getSecurityString(context, DEVELOP_CONFIG_SET);
        if(TextUtils.isEmpty(json)) {
            return new HashMap<>();
        }
        return CommonJSONParserHelper.jsonToMap(json);
    }

    public int put(Context context, String key, String value) {
        try {
            developConfig = getConfigMap(context);
        } catch (JSONException e) {
            e.printStackTrace();
            return -1;
        }
        developConfig.put(key, value);
        return putMapData(context, DEVELOP_CONFIG_SET, developConfig);
    }

    public Object get(Context context, String key) {
        try {
            developConfig = getConfigMap(context);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return developConfig == null ? null : developConfig.get(key);
    }

    public void remove(Context context, String key) {
        try {
            developConfig = getConfigMap(context);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if(developConfig == null) {
            return;
        }
        developConfig.remove(key);
        putMapData(context, DEVELOP_CONFIG_SET, developConfig);
    }

    public void clear(Context context, String key) {
        removeSecurityString(context, key);
    }
}
