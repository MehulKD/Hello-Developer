package sj.hello_developer.data.bean;

import android.view.View;

/**
 * Created by sj on 05/12/2016.
 */

public class HostBean   {

    public String name;
    public String url;
    public View.OnClickListener onClickListener;

    public HostBean(String name, String url, View.OnClickListener onClickListener) {
        this.name = name;
        this.url = url;
        this.onClickListener = onClickListener;
    }
}
