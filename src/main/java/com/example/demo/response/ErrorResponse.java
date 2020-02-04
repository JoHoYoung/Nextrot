package com.example.demo.response;

import com.example.demo.ErrorCode;
import com.example.demo.exception.BusinessException;

public class ErrorResponse extends BaseResponse {

  private String code;

  public ErrorResponse(BusinessException e) {
    super(e.getErrorCode().getStatus(), e.getErrorCode().getStatusMsg());
    this.code = e.getErrorCode().getStatusCode();
  }
}
