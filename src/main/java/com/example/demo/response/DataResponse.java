package com.example.demo.response;

public class DataResponse<T> extends BaseResponse{
  public T data;
  public DataResponse(int statusCode, String statusMsg, T data){
    super(statusCode, statusMsg);
    this.data = data;
  }
}
