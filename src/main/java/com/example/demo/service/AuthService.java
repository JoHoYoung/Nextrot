package com.example.demo.service;

import com.example.demo.ErrorCode;
import com.example.demo.exception.TokenExpiredException;
import com.example.demo.exception.TokenInvalidException;
import io.jsonwebtoken.*;
import reactor.core.publisher.Mono;

import java.util.Date;

public interface AuthService<T> {


  String genAccessToken(Object subject);

  String genRefreshToken(Object subject);

  void verifyToken(String token);

  T decode(String token);

}
