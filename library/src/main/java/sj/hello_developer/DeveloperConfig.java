package sj.hello_developer;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import java.util.ArrayList;

import sj.hello_developer.data.bean.HostBean;
import sj.hello_developer.data.bean.SwitchBean;

/**
 * Created by sj on 05/12/2016.
 */

public class DeveloperConfig {

    public final static int DEVELOP_REQUEST_CODE = 10000;
    public final static Class DEFALUT_CLASS = DeveloperActivity.class;

    static Builder builder;

    public static Builder getGlobalDeveloperConfig() {
        return builder;
    }

    public static void clearGlobalDeveloperConfig() {
        builder = null;
    }

    private DeveloperConfig(Context context, Builder builder) {
        this.builder = builder;
        startDevelop(context, builder._class);
    }

    private void startDevelop(Context context, Class _class) {
        _class = _class == null ? DeveloperActivity.class : _class;
        Intent intent = new Intent();
        intent.setClass(context, _class);
        if (context instanceof Activity) {
            ((Activity) context).startActivityForResult(intent, DEVELOP_REQUEST_CODE);
        } else {
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        }
    }

    public static class Builder {

        public Context context;
        public Class _class = DEFALUT_CLASS;
        public ArrayList<SwitchBean> switches;
        public ArrayList<HostBean> hostBeens;
        public String defaultHost;
        public String detailInfo;
        public DeveloperActivity.UserDefHostChangeListener userDefHostChangeListener;
        public int iconLogo;
        public Activity defaultActivity;

        public Builder(Context context) {
            if (context == null) {
                throw new IllegalArgumentException("A non-null Context must be provided");
            }
            this.context = context;
        }

        public Builder addSwitch(SwitchBean... sw) {
            if (sw == null) {
                return this;
            }
            switches = new ArrayList<>();
            for (SwitchBean bean : sw) {
                switches.add(bean);
            }
            return this;
        }

        public Builder addHost(HostBean... hb) {
            if (hb == null) {
                return this;
            }
            hostBeens = new ArrayList<>();
            for (HostBean bean : hb) {
                hostBeens.add(bean);
            }
            return this;
        }

        public Builder setUserDefHostChangeListener(DeveloperActivity.UserDefHostChangeListener userDefHostChangeListener) {
            this.userDefHostChangeListener = userDefHostChangeListener;
            return this;
        }

        public Builder setDefaultHost(String defaultHost) {
            this.defaultHost = defaultHost;
            return this;
        }

        public Builder setIcon(int iconLogo) {
            this.iconLogo = iconLogo;
            return this;
        }

        public Builder setDetailInfo(String detailInfo) {
            this.detailInfo = detailInfo;
            return this;
        }

        public Builder setActivity(Class _c) {
            if (_c != null) {
                this._class = _c;
            }
            return this;
        }

        public DeveloperConfig build() {
            return new DeveloperConfig(context, this);
        }
    }
}
