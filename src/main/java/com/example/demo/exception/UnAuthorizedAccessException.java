package com.example.demo.exception;


import com.example.demo.ErrorCode;

public class UnAuthorizedAccessException extends BusinessException{
  public UnAuthorizedAccessException(ErrorCode errorCode){
    super(errorCode);
  }
}
