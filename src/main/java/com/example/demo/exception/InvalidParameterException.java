package com.example.demo.exception;

import com.example.demo.ErrorCode;

public class InvalidParameterException extends BusinessException{
  public  InvalidParameterException(ErrorCode errorCode){
    super(errorCode);
  }
}
