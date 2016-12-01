package sj.hello_developer.util;

/**
 * Created by sj on 01/12/2016.
 */

public class DTimeUtil implements ITimeFormat {

    public static String GetFullTime(long timeMillis) {
        return dateFormaterWhole.get().format(timeMillis);
    }
}
