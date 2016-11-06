package com.rushabh.subreddit;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by rushabh on 06/11/16.
 */

public class Utility {

    public static String getTime(long timestamp){
        Date date=new Date(timestamp);
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd/MM/yy hh:mm a");
        return simpleDateFormat.format(date);
    }
}
