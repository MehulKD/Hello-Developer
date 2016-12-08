package sj.hello_developer.simple;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.view.View;
import android.widget.CompoundButton;

import sj.hello_developer.DeveloperActivity;
import sj.hello_developer.DeveloperConfig;
import sj.hello_developer.data.DeveloperConfigManage;
import sj.hello_developer.data.bean.HostBean;
import sj.hello_developer.data.bean.SwitchBean;

/**
 * Created by sj on 07/12/2016.
 */

public class DeveloperHelper {

    public static String CONFIG_KEY_OPEN_DEVELOPMODEL = "OPEN_DEVELOPMODEL";
    public static String CONFIG_KEY_URL = "URL";
    public static String CONFIG_KEY_LOG = "LOG";
    public static String CONFIG_KEY_GUIDE = "GUIDE";
    public static String CONFIG_KEY_AD = "AD";

    public static void open(final Context context) {

        final DeveloperConfig.Builder builder = new DeveloperConfig.Builder(context);
        SwitchBean SwitchBean = new SwitchBean();
        SwitchBean.key = CONFIG_KEY_LOG;
        SwitchBean.title = "Log控制";
        SwitchBean.desc = "是否支持输出Log";
        SwitchBean.selectState = "1".equals(DeveloperConfigManage.getInstance().get(context, CONFIG_KEY_LOG)) ? true : false;
        SwitchBean.onCheckedChangeListener = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                DeveloperConfigManage.getInstance().put(context, CONFIG_KEY_LOG, isChecked ? "1" : "0");
            }
        };

        SwitchBean SwitchBeanGuide = new SwitchBean();
        SwitchBeanGuide.key = CONFIG_KEY_GUIDE;
        SwitchBeanGuide.title = "总是显示启动页";
        SwitchBeanGuide.desc = "是否总是显示启动页";
        SwitchBeanGuide.selectState = "1".equals(DeveloperConfigManage.getInstance().get(context, CONFIG_KEY_GUIDE)) ? true : false;
        SwitchBeanGuide.onCheckedChangeListener = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                DeveloperConfigManage.getInstance().put(context, CONFIG_KEY_GUIDE, isChecked ? "1" : "0");
            }
        };

        SwitchBean SwitchBeanAd = new SwitchBean();
        SwitchBeanAd.key = CONFIG_KEY_AD;
        SwitchBeanAd.title = "显示广告页";
        SwitchBeanAd.desc = "是否显示广告页";
        SwitchBeanAd.selectState = "1".equals(DeveloperConfigManage.getInstance().get(context, CONFIG_KEY_AD)) ? true : false;
        SwitchBeanAd.onCheckedChangeListener = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                DeveloperConfigManage.getInstance().put(context, CONFIG_KEY_AD, isChecked ? "1" : "0");
            }
        };

        HostBean hostBeanUAT = new HostBean("切换 UAT", "http://test-sj.hello_developer.com.cn:12015/", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                crashToRestart(builder, "http://test-sj.hello_developer.com.cn:12015/");
            }
        });

        HostBean hostBeanDEV = new HostBean("切换 DEV", "http://test-dd.hello_developer.com.cn:12015/", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                crashToRestart(builder, "http://test-dd.hello_developer.com.cn:12015/");
            }
        });

        HostBean hostBeanAwsTest = new HostBean("切换 AWSTest", "http://ck-sh.hello_developer.com.cn:12016/", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                crashToRestart(builder, "http://ck-sh.hello_developer.com.cn:12016/");
            }
        });

        HostBean hostBeanAwsTESS = new HostBean("切换 AWS", "http://ck-sh.hello_developer.com.cn:12015/", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                crashToRestart(builder, "http://ck-sh.hello_developer.com.cn:12015/");
            }
        });

        builder.addSwitch(SwitchBean, SwitchBeanGuide, SwitchBeanAd)
                .addHost(hostBeanUAT, hostBeanDEV, hostBeanAwsTest, hostBeanAwsTESS)
                .setUserDefHostChangeListener(new DeveloperActivity.UserDefHostChangeListener() {
                    @Override
                    public void change(String url) {
                        DeveloperConfigManage.getInstance().put(context, CONFIG_KEY_URL, url);
                    }
                })
                .setDefaultHost("http://ck-sh.hello_developer.com.cn:12015/")
                .setDetailInfo("")
                .setIcon(R.mipmap.ic_launcher)
                .build();
    }

    public static void crashToRestart(final DeveloperConfig.Builder builder, final String url) {
        if (builder.defaultActivity != null) {
            new AlertDialog.Builder(builder.defaultActivity)
                    .setTitle("切换HOST")
                    .setMessage("将会通过程序崩溃的方式重新设置key，请手动重启，确定？")
                    .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (!TextUtils.isEmpty(url)) {
                                DeveloperConfigManage.getInstance().put(builder.defaultActivity, CONFIG_KEY_URL, url);
                            }
                            throw new IllegalStateException("dev restart");
                        }
                    })
                    .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    }).show();
        }
    }
}
