package cn.mldn.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
	public static String getFormatDatetime() {
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) ;
	}
	public static String getFormatDatetime(Date date) {
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date) ; 
	}
}
