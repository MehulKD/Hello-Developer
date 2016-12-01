package sj.hello_developer;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import java.io.File;

import sj.hello_developer.util.DTimeUtil;

/**
 * Created by sj on 2016/11/30.
 */

public class DeveloperActivity extends AppCompatActivity {

    TextView tvInfo;

    public static void startActivity(Context mContext) {
        Intent intent = new Intent(mContext, DeveloperActivity.class);
        mContext.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_developer);
        initView();
    }

    private void initView() {
        tvInfo = (TextView)findViewById(R.id.tv_info);
        initInfoData();
    }

    private void initInfoData() {
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
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (!TextUtils.isEmpty(stringBuffer.toString())) {
            tvInfo.setVisibility(View.VISIBLE);
            tvInfo.setText(stringBuffer.toString());
        }
    }
}
