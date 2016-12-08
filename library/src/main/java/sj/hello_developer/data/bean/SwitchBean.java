package sj.hello_developer.data.bean;

import android.widget.CompoundButton;

/**
 * Created by sj on 05/12/2016.
 */

public class SwitchBean {

    public String key;
    public String value;
    public String title;
    public String select;
    public String unselect;
    public String desc;
    public boolean selectState;
    public CompoundButton.OnCheckedChangeListener onCheckedChangeListener;

    public SwitchBean() {
    }

}
