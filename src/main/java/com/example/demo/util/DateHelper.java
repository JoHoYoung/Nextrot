package com.example.demo.util;

import com.example.demo.ErrorCode;
import com.example.demo.exception.DateParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class DateHelper {

  @Value("${base.date-format}")
  private String baseForamt;

  public String DateToStringFormat(String format, Date time) {
    SimpleDateFormat transFormat = new SimpleDateFormat(format);
    return transFormat.format(time);
  }

  public String DefaultDateToStringFormat(String format) {
    SimpleDateFormat transFormat = new SimpleDateFormat(format);
    return transFormat.format(new Date());
  }

  public String DefaultDateToDefalutString() {
    SimpleDateFormat transFormat = new SimpleDateFormat(this.baseForamt);
    return transFormat.format(new Date());
  }

  public String DateToDefaultStringForamt(Date time) {
    SimpleDateFormat transFormat = new SimpleDateFormat(this.baseForamt);
    return transFormat.format(time);
  }

  public Date StringFormatToDate(String format, String time) {
    try {
      SimpleDateFormat transFormat = new SimpleDateFormat(format);
      return transFormat.parse(time);
    } catch (ParseException e) {
      throw new DateParseException(ErrorCode.DATE_PARSE_ERROR);
    }
  }

  public Date StingToDate(String time) {
    try {
      SimpleDateFormat transFormat = new SimpleDateFormat(this.baseForamt);
      return transFormat.parse(time);
    } catch (ParseException e) {
      throw new DateParseException(ErrorCode.DATE_PARSE_ERROR);
    }
  }
}

