package com.example.demo;

import com.example.demo.exception.DateParseException;
import com.example.demo.exception.EmptyDataException;
import com.example.demo.response.BaseResponse;
import com.example.demo.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class BusinessExceptionHandler {

  @ExceptionHandler(EmptyDataException.class)
  public ResponseEntity<ErrorResponse> EmptyDataExceptionHandler(EmptyDataException e){
    return new ResponseEntity<>(new ErrorResponse(e), HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(DateParseException.class)
  public ResponseEntity<ErrorResponse> DateParseExceptionHandler(DateParseException e){
    return new ResponseEntity<>(new ErrorResponse(e), HttpStatus.BAD_REQUEST);
  }

}
