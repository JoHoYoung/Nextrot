package com.example.demo;

import com.example.demo.exception.*;
import com.example.demo.response.ErrorResponse;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;

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

  @ExceptionHandler(InvalidParameterException.class)
  protected ResponseEntity<ErrorResponse> InvalidParameterExceptionHandler(InvalidParameterException e){
      return new ResponseEntity<>(new ErrorResponse(e), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(WebExchangeBindException.class)
  protected ResponseEntity<ErrorResponse> WebExchangeBindExceptionHanldler(WebExchangeBindException e){
    throw new InvalidParameterException(ErrorCode.INVALID_REQUEST_PARAMETER);
  }

  @ExceptionHandler(InternalServerException.class)
  protected ResponseEntity<ErrorResponse> InternalServerExceptionHandler(InternalServerException e){
    return new ResponseEntity<>(new ErrorResponse(e), HttpStatus.BAD_REQUEST);
  }
}
