package sj.hello_developer;

import android.app.Activity;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import java.io.File;

import sj.hello_developer.data.bean.HostBean;
import sj.hello_developer.data.bean.SwitchBean;
import sj.hello_developer.util.DTimeUtil;

/**
 * Created by sj on 2016/11/30.
 */

public class DeveloperActivity extends Activity {

    protected TextView tvInfo;
    protected LinearLayout lyHostlist;
    protected LinearLayout lySwitchlist;
    protected TextView tv_host;
    protected Button btn_hostchange;
    protected ImageView iv_logo;
    protected EditText et_url;

    protected DeveloperConfig.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_developer);
        initView();
        initData();
    }

    protected void initView() {
        tvInfo = (TextView) findViewById(R.id.tv_info);
        lySwitchlist = (LinearLayout) findViewById(R.id.ly_switchlist);
        lyHostlist = (LinearLayout) findViewById(R.id.ly_hostlist);
        tv_host = (TextView) findViewById(R.id.tv_host);
        btn_hostchange = (Button) findViewById(R.id.btn_hostchange);
        iv_logo = (ImageView) findViewById(R.id.iv_logo);
        et_url = (EditText) findViewById(R.id.et_url);
    }

    protected void initData() {
        builder = DeveloperConfig.getGlobalDeveloperConfig();
        if(builder == null) {
            return;
        }
        builder.defaultActivity = this;
        initSwitchList();
        initHostList();
        initInfoData();
        initLogo();
    }

    /**
     * 初始化switch 列表数据
     */
    protected void initSwitchList() {
        if(builder.switches == null) {
            return;
        }
        for(SwitchBean switchBean : builder.switches) {
            Switch switchLog = new Switch(this);
            switchLog.setText(switchBean.title);
            int padding = (int) getResources().getDimension(R.dimen.develop_common_padding);
            switchLog.setPadding(padding, padding, padding, padding);
            switchLog.setChecked(switchBean.selectState);
            switchLog.setTextColor(getResources().getColor(R.color.develop_blackDark));
            switchLog.setOnCheckedChangeListener(switchBean.onCheckedChangeListener);
            lySwitchlist.addView(switchLog);

            TextView descTextView = new TextView(this);
            descTextView.setText(switchBean.desc);
            descTextView.setPadding(padding, 0, padding, padding);
            descTextView.setTextColor(getResources().getColor(R.color.develop_blackDark));
            lySwitchlist.addView(descTextView);
        }
    }

    /**
     * 初始化host 列表数据
     */
    protected void initHostList() {
        if(builder.hostBeens == null) {
            return;
        }
        boolean isFrist = true;
        for (HostBean host : builder.hostBeens) {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

            TextView textView = new TextView(this);
            textView.setText(host.url);
            textView.setTextSize(14);
            textView.setTextColor(getResources().getColor(R.color.develop_blackDark));
            if (!isFrist) {
                params.topMargin = (int) getResources().getDimension(R.dimen.develop_common_padding_little);
            }
            lyHostlist.addView(textView, params);

            Button button = new Button(this);
            button.setText(host.name);
            button.setTextSize(14);
            params.topMargin = 0;
            button.setOnClickListener(host.onClickListener);
            lyHostlist.addView(button, params);

            isFrist = false;
        }

        if(builder.userDefHostChangeListener != null) {
            setOnUserDefHostChange(builder.userDefHostChangeListener);
        }

        btn_hostchange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(userDefHostChangeListener != null) {
                    userDefHostChangeListener.change(et_url.getText().toString());
                }
            }
        });

        initDefaultHost();
    }

    protected void initDefaultHost() {
        tv_host.setText("当前环境：\r\n" + builder.defaultHost);
    }

    /**
     * 初始化其他数据
     */
    protected void initInfoData() {
        StringBuffer stringBuffer = new StringBuffer();
        PackageManager packageManager = this.getPackageManager();
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(getApplication().getPackageName(), 0);
            ApplicationInfo applicationInfo = packageInfo.applicationInfo;
            String name = applicationInfo.loadLabel(getPackageManager()).toString();
            stringBuffer.append("程序名称:\n" + name);
            String packageName = applicationInfo.packageName;
            stringBuffer.append("\n" + "程序包名:\n" + packageName);

            float size = new File(packageInfo.applicationInfo.publicSourceDir).length();
            if (size > 0) {
                size = size / 1024 / 1024;
            }
            stringBuffer.append("\n" + "程序大小:\n" + size + "MB");
            int versionCode = packageInfo.versionCode;
            stringBuffer.append("\n" + "版本编号:\n" + versionCode);
            String versionName = packageInfo.versionName;
            stringBuffer.append("\n" + "版本名称:\n" + versionName);
            String permission = applicationInfo.permission;
            stringBuffer.append("\n" + "程序权限:\n" + permission);
            stringBuffer.append("\n" + "文件路径:\n" + packageInfo.applicationInfo.publicSourceDir);
            long firstInstallTime = packageInfo.firstInstallTime;
            stringBuffer.append("\n" + "安装时间:\n" + DTimeUtil.GetFullTime(firstInstallTime));
            stringBuffer.append("\n" + "最后修改:\n" + DTimeUtil.GetFullTime(new File(packageInfo.applicationInfo.publicSourceDir).lastModified()));

            if(!TextUtils.isEmpty(builder.detailInfo)) {
                stringBuffer.append("\n\n" + builder.detailInfo);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (!TextUtils.isEmpty(stringBuffer.toString())) {
            tvInfo.setVisibility(View.VISIBLE);
            tvInfo.setText(stringBuffer.toString());
        }
    }

    /**
     * 初始化logo
     */
    protected void initLogo() {
        iv_logo.setImageResource(builder.iconLogo);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        DeveloperConfig.clearGlobalDeveloperConfig();
    }

    public void setOnUserDefHostChange(UserDefHostChangeListener userDefHostChangeListener) {
        this.userDefHostChangeListener = userDefHostChangeListener;
    }

    UserDefHostChangeListener userDefHostChangeListener;

    public interface UserDefHostChangeListener{
        void change(String url);
    }
}
