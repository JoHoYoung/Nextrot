package com.example.demo.exception;

import com.example.demo.ErrorCode;

public class EmptyDataException extends BusinessException{
  public EmptyDataException(ErrorCode errorCode){
    super(errorCode);
  }
}
