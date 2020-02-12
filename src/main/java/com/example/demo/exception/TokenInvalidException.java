package com.example.demo.exception;

import com.example.demo.ErrorCode;

public class TokenInvalidException extends BusinessException{
  public TokenInvalidException(ErrorCode errorCode){
    super(errorCode);
  }
}
