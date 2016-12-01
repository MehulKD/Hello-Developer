package sj.hello_developer.util;

import java.text.SimpleDateFormat;

/**
 * Created by sj on 01/12/2016.
 */

public interface ITimeFormat {

    ThreadLocal<SimpleDateFormat> dateFormaterWhole = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yy-MM-dd HH:mm:ss");
        }
    };
}
