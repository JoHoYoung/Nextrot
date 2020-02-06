package com.example.demo.controller;

import com.example.demo.model.Session;
import com.example.demo.request.GenToken;
import com.example.demo.response.AuthJwtResponse;
import com.example.demo.response.BaseResponse;
import com.example.demo.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

  @Autowired
  AuthService<Session> authService;

  @PostMapping("/token")
  public Mono<ResponseEntity<BaseResponse>> genToken(@RequestBody GenToken genToken) {
    return Flux.mergeSequential(authService.genAccessToken(new Session(genToken.getUID())),
      authService.genRefreshToken(genToken)).collectList().map(tokens -> {
      final BaseResponse response = new AuthJwtResponse(200, "success", tokens.get(0), tokens.get(1));
      return new ResponseEntity<>(response, HttpStatus.OK);
    });
  }

  @PostMapping("/token/refresh")
  public Mono<ResponseEntity<BaseResponse>> refreshToken(@RequestParam("refreshToken") String refreshToken) {
    return authService.verifyToken(refreshToken)
      .thenReturn(authService.decode(refreshToken).map(decoded -> {
        return authService.genRefreshToken(decoded.toString()).map(refreshedToken -> {
          final BaseResponse response = new AuthJwtResponse(200, "successs", refreshedToken, refreshToken);
          return new ResponseEntity(response, HttpStatus.OK);
        }).block();
      }).block());
  }
}
