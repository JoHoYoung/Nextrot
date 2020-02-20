package com.example.demo;

import com.example.demo.exception.BusinessException;
import com.example.demo.exception.InternalServerException;
import com.example.demo.util.DataBufferWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;


@Component
public class WebExceptionHandler implements ErrorWebExceptionHandler {

  @Autowired
  DataBufferWriter dataBufferWriter;

  @Override
  public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
    try{
      return dataBufferWriter.write(exchange.getResponse(), (BusinessException)ex);
    }
    catch(ClassCastException e){
      return dataBufferWriter.write(exchange.getResponse(), new InternalServerException(ErrorCode.INTERNAL_SERVER_ERROR));
    }
  }
}
