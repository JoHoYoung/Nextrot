package com.example.demo;

import com.example.demo.exception.*;
import com.example.demo.response.BaseResponse;
import com.example.demo.response.ErrorResponse;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerExceptionHandler {

  @ExceptionHandler(EmptyDataException.class)
  public ResponseEntity<ErrorResponse> EmptyDataExceptionHandler(EmptyDataException e){
    return new ResponseEntity<>(new ErrorResponse(e), HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(DateParseException.class)
  public ResponseEntity<ErrorResponse> DateParseExceptionHandler(DateParseException e){
    return new ResponseEntity<>(new ErrorResponse(e), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(TokenExpiredException.class)
  public ResponseEntity<ErrorResponse> TokenExpiredExceptionHandler(TokenExpiredException e){
    return new ResponseEntity<>(new ErrorResponse(e), HttpStatus.FORBIDDEN);
  }

  @ExceptionHandler(TokenInvalidException.class)
  public ResponseEntity<ErrorResponse> TokenInvalidExceptionHandler(TokenInvalidException e){
    return new ResponseEntity<>(new ErrorResponse(e), HttpStatus.FORBIDDEN);
  }

  @ExceptionHandler(UnAuthorizedAccessException.class)
  public ResponseEntity<ErrorResponse> UnAuthorizedAccessExceptionHandler(UnAuthorizedAccessException e){
    return new ResponseEntity<>(new ErrorResponse(e), HttpStatus.UNAUTHORIZED);
  }

}
