package com.example.demo.util;

import org.springframework.beans.factory.annotation.Value;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateHelper {

  @Value("base.data-format")
  private static String baseForamt;

  public static String getDateToStringFormat(String format, Date time) {
    SimpleDateFormat transFormat = new SimpleDateFormat(format);
    return transFormat.format(time);
  }

  public static String getDateToStringFormat(String format) {
    SimpleDateFormat transFormat = new SimpleDateFormat(format);
    return transFormat.format(new Date());
  }

  public static String getDateToString() {
    SimpleDateFormat transFormat = new SimpleDateFormat(DateHelper.baseForamt);
    return transFormat.format(new Date());
  }

  public static String getDateToString(Date time) {
    SimpleDateFormat transFormat = new SimpleDateFormat(DateHelper.baseForamt);
    return transFormat.format(time);
  }

  public static Date getStringToDateFormat(String format, String time) {
    try {
      SimpleDateFormat transFormat = new SimpleDateFormat(format);
      return transFormat.parse(time);
    } catch (ParseException e) {
  //    throw new DateParseException(ErrorCode.DATE_PARSE_ERROR);
      return null;
    }
  }

  public static Date getStingToDate(String time) {
    try {
      SimpleDateFormat transFormat = new SimpleDateFormat(DateHelper.baseForamt);
      return transFormat.parse(time);
    } catch (ParseException e) {
    //  throw new DateParseException(ErrorCode.DATE_PARSE_ERROR);
      return null;
    }
  }
}

