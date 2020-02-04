package com.example.demo;

public enum ErrorCode {

  EMPTY_DATA_SET(701,"A001", "Empty Data Set"),
  INVALID_KEY(702, "A002", "Invalid Key"),

  DATE_PARSE_ERROR(471,"B001", "Date Parse Error");

  private final String statusCode;
  private final String statusMsg;
  private int status;

  ErrorCode(final int status, final String code, final String message) {
    this.status = status;
    this.statusMsg = message;
    this.statusCode = code;
  }

  public int getStatus(){
    return this.status;
  }
  public String getStatusCode(){
    return this.statusCode;
  }
  public String getStatusMsg(){
    return this.statusMsg;
  }
}
