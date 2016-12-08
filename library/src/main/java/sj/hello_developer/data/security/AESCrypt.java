package sj.hello_developer.data.security;

import android.text.TextUtils;
import java.security.GeneralSecurityException;

/**
 * Created by sj on 10/22/16.
 */

public class AESCrypt extends AESCryptBase {

    private AESCrypt() {
        super();
    }

    public static String encryptStr(String salt, String content) {
        if (TextUtils.isEmpty(content) || TextUtils.isEmpty(salt)) {
            return "";
        }
        try {
            return encrypt(salt, content);
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String decryptStr(String salt, String content) {
        if (TextUtils.isEmpty(content)) {
            return "";
        }
        try {
            return decrypt(salt, content);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}
