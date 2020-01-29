package com.example.demo.Response;

import javax.xml.crypto.Data;

public class DataResponse<T> extends BaseResponse{
  public T data;
  public DataResponse(int statusCode, String statusMsg, T data){
    super(statusCode, statusMsg);
    this.data = data;
  }
}
