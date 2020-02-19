package com.example.demo.exception;

import com.example.demo.ErrorCode;

public class InternalServerException extends BusinessException {
  public InternalServerException(ErrorCode errorCode){
    super(errorCode);
  }
}
