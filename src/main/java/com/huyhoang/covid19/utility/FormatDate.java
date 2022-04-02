package com.huyhoang.covid19.utility;

import java.text.SimpleDateFormat;

import org.springframework.expression.ParseException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class FormatDate {

	private final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
	 
	private final SimpleDateFormat DATE_TIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	 
	public java.util.Date parseDate(String date) throws java.text.ParseException {
	    try {
	        return DATE_FORMAT.parse(date);
	    } catch (ParseException e) {
	        throw new IllegalArgumentException(e);
	    }
	}
	 
	public java.util.Date parseTimestamp(String timestamp) throws java.text.ParseException {
	    try {
	        return DATE_TIME_FORMAT.parse(timestamp);
	    } catch (ParseException e) {
	        throw new IllegalArgumentException(e);
	    }
	}
	
}
