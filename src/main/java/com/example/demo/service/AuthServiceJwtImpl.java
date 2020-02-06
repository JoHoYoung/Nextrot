package com.example.demo.service;


import com.example.demo.ErrorCode;
import com.example.demo.exception.TokenExpiredException;
import com.example.demo.exception.TokenInvalidException;
import com.example.demo.model.Session;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.*;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.Date;

@Service
public class AuthServiceJwtImpl implements AuthService<Session> {

  @Value("nextrot.jwt.secret")
  private String SALT;

  @Autowired
  ObjectMapper objectMapper;

  public Mono<String> genAccessToken(Object subject) {
    try{

      Date Now = new Date();
      Date expireTime = new Date(Now.getTime() + 1000 * 60 * 60 * 24 * 14);
      String jwt = Jwts.builder()
        .setExpiration(expireTime)
        .setSubject(objectMapper.writeValueAsString(subject))
        .signWith(SignatureAlgorithm.HS256, SALT)
        .compact();
      return Mono.just(jwt);
    }catch (JsonProcessingException e){
      throw new TokenInvalidException(ErrorCode.INVALID_TOKEN);
    }
  }

  public Mono<String> genRefreshToken(Object subject) {
    Date Now = new Date();
    Date expireTime = new Date(Now.getTime() + 1000 * 60 * 60 * 24 * 365);
    String jwt = Jwts.builder()
      .setExpiration(expireTime)
      .setSubject(subject.toString())
      .signWith(SignatureAlgorithm.HS256, SALT)
      .compact();
    return Mono.just(jwt);
  }

  public Mono<Void> verifyToken(String token) {
    try {
      Jwts.parser().setSigningKey(SALT).parseClaimsJws(token).getBody();
      return Mono.just(null);
    } catch (ExpiredJwtException e) {
      throw new TokenExpiredException(ErrorCode.JWT_TOKEN_EXPIRED);
    } catch (JwtException e) {
      throw new TokenInvalidException(ErrorCode.INVALID_TOKEN);
    }
  }

  // Token 해독 및 객체 생성
  public Mono<Session> decode(String token) {
    try {
      Claims Claim = Jwts.parser().setSigningKey(SALT).parseClaimsJws(token).getBody();
      Session session = objectMapper.readValue(Claim.getSubject(), Session.class);
      return Mono.just(session);
    } catch (JsonProcessingException e) {
      throw new TokenInvalidException(ErrorCode.INVALID_TOKEN);
    } catch (IOException e) {
      throw new TokenInvalidException(ErrorCode.INVALID_TOKEN);
    }
  }
}