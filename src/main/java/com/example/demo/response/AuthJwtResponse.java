package com.example.demo.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthJwtResponse extends BaseResponse {

  private String accessToken;
  private String refreshToken;

  public AuthJwtResponse(int statusCode, String statusMsg, String accessToken, String refreshToken){
    super(statusCode,statusMsg);
    this.accessToken = accessToken;
    this.refreshToken = refreshToken;
  }

}
