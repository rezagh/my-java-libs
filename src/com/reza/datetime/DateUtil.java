package com.reza.datetime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.time.DateUtils;

public class DateUtil {
	private static String DATE_FORMAT = "dd/MMM/yyyy";

	public static Date toDate(String date){
		try {
			String [] a = {DATE_FORMAT};
			return DateUtils.parseDate(date, a);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String simpleDate(long time){
	    SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
		return sdf.format(time);
	}
}
