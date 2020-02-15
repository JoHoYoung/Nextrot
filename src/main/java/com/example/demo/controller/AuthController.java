package com.example.demo.controller;

import com.example.demo.model.Session;
import com.example.demo.request.GenToken;
import com.example.demo.request.TokenRefresh;
import com.example.demo.response.AuthJwtResponse;
import com.example.demo.response.BaseResponse;
import com.example.demo.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

  @Autowired
  AuthService<Session> authService;

  @PostMapping("/token")
  public Mono<ResponseEntity<BaseResponse>> genToken(@Valid @RequestBody GenToken genToken) {

    final BaseResponse response = new AuthJwtResponse(200, "success",
      authService.genAccessToken(new Session(genToken.getUID())),
      authService.genRefreshToken(genToken));
    return Mono.just(new ResponseEntity<>(response, HttpStatus.OK));
  }

  @PostMapping("/token/refresh")
  public Mono<ResponseEntity<BaseResponse>> refreshToken(@RequestBody TokenRefresh refreshToken) {
    authService.verifyToken(refreshToken.getRefershToken());
    final BaseResponse response = new AuthJwtResponse(200, "successs",
      authService.genAccessToken(authService.decode(refreshToken.getRefershToken())), refreshToken.getRefershToken());
    return Mono.just(new ResponseEntity(response, HttpStatus.OK));
  }
}

