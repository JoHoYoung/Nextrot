package com.example.demo.exception;


import com.example.demo.ErrorCode;
public class TokenExpiredException extends BusinessException{
  public TokenExpiredException(ErrorCode errorcode){
    super(errorcode);
  }
}

