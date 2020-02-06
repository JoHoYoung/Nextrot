package com.example.demo.service;

import com.example.demo.ErrorCode;
import com.example.demo.exception.TokenExpiredException;
import com.example.demo.exception.TokenInvalidException;
import io.jsonwebtoken.*;
import reactor.core.publisher.Mono;

import java.util.Date;

public interface AuthService<T> {

  Mono<String> genAccessToken(Object subject);

  Mono<String> genRefreshToken(Object subject);

  Mono<Void> verifyToken(String token);

  Mono<T> decode(String token);

}
