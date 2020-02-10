package com.example.demo;

public enum ErrorCode {

  EMPTY_DATA_SET(701,"A001", "Empty Data Set"),
  INVALID_KEY(702, "A002", "Invalid Key"),

  DATE_PARSE_ERROR(721,"B001", "Date Parse Error"),

  // JWT Token
  JWT_TOKEN_EXPIRED(731,"C001","Token Expired"),
  INVALID_TOKEN(732,"C002","Invalid Token"),
  EMPTY_TOKEN(733,"C003","Empty Token"),

  DUPLICATED_LIKE(741, "D001", "Duplicate Like cannot be done"),

  INVALID_REQUEST_PARAMETER(751, "E001", "Invalid Request Paramerter Format"),
  DECODED_TOKEN_PARSE_ERROR(431,"A004","Error at Parse decoded Token");

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
