package com.example.demo.service;


import com.example.demo.ErrorCode;
import com.example.demo.exception.TokenExpiredException;
import com.example.demo.exception.TokenInvalidException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.*;
import reactor.core.publisher.Mono;

import java.util.Date;

@Service
public class AuthServiceJwtImpl implements AuthService{

  @Value("nextrot.jwt.secret")
  private String SALT;

  public Mono<String> genAccessToken(Object subject) {
    Date Now = new Date();
    Date expireTime = new Date(Now.getTime() + 1000 * 60 * 60 * 24 * 14);
    String jwt = Jwts.builder()
      .setExpiration(expireTime)
      .setSubject(subject.toString())
      .signWith(SignatureAlgorithm.HS256, SALT)
      .compact();

    return Mono.just("ACC");
  }

  public Mono<String> genRefreshToken(Object subject) {
    Date Now = new Date();
    Date expireTime = new Date(Now.getTime() + 1000 * 60 * 60 * 24 * 365);
    String jwt = Jwts.builder()
      .setExpiration(expireTime)
      .setSubject(subject.toString())
      .signWith(SignatureAlgorithm.HS256, SALT)
      .compact();
    return Mono.just("REF");
  }

  public Mono<Void> verifyToken(String token) {
    try {
      Jwts.parser().setSigningKey(SALT).parseClaimsJws(token).getBody();
      return Mono.just(null);
    } catch (ExpiredJwtException e) {
      System.out.println(decode(token));
      throw new TokenExpiredException(ErrorCode.JWT_TOKEN_EXPIRED);
    } catch (JwtException e) {
      System.out.println(e);
      throw new TokenInvalidException(ErrorCode.INVALID_TOKEN);
    }
  }

  // Token 해독 및 객체 생성
  public  Mono<String> decode(String token) {
    Claims Claim = Jwts.parser().setSigningKey(SALT).parseClaimsJws(token).getBody();
    return Mono.just(Claim.getSubject());
  }

}