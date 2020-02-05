package com.example.demo.config;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.HandlerFilterFunction;
import org.springframework.web.reactive.function.server.HandlerFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
public class FilterConfig implements WebFilter {

  public Mono<Void> filter(ServerWebExchange serverWebExchange, WebFilterChain webFilterChain){
    List<String> authHeaders = serverWebExchange.getRequest().getHeaders().get("Authorization");
    String token = authHeaders.get(0);

    return webFilterChain.filter(serverWebExchange);
  }
}
