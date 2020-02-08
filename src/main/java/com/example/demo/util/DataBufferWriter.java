package com.example.demo.util;

import com.example.demo.exception.BusinessException;
import com.example.demo.response.ErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class DataBufferWriter {
  private final ObjectMapper objectMapper;

  public Mono<Void> write(ServerHttpResponse httpResponse, BusinessException object) {
    return httpResponse
      .writeWith(Mono.fromSupplier(() -> {
        DataBufferFactory bufferFactory = httpResponse.bufferFactory();
        try {
          return bufferFactory.wrap(objectMapper.writeValueAsBytes(new ErrorResponse(object)));
        } catch (Exception ex) {
          return bufferFactory.wrap(new byte[0]);
        }
      }));
  }
}
