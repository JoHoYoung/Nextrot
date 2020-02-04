package com.example.demo.exception;

import com.example.demo.ErrorCode;

public class DateParseException extends BusinessException {
  public DateParseException(ErrorCode errorCode){
    super(errorCode);
  }
}
