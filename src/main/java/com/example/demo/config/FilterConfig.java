package com.example.demo.config;

import com.example.demo.ErrorCode;
import com.example.demo.exception.TokenInvalidException;
import com.example.demo.service.AuthService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
public class FilterConfig implements WebFilter {

  @Autowired
  ObjectMapper objectMapper;

  @Autowired
  AuthService authService;

  static private Log logger = LogFactory.getLog("com.example.demo.config");
  String[] excludePatterns = new String[]{"/api/v1/auth/token",
    "/api/v1/auth/token/refresh", "/api/v1/admin/data", "/api/v1/admin/update"};

  public Mono<Void> filter(ServerWebExchange serverWebExchange, WebFilterChain webFilterChain) {
    logger.info(serverWebExchange.getRequest().getPath() + " " +serverWebExchange.getRequest().getHeaders().get("X-Real-IP"));
    for (String excludePattern : this.excludePatterns) {
      if (serverWebExchange.getRequest().getPath().toString().matches(excludePattern)) {
        return webFilterChain.filter(serverWebExchange);
      }
    }
    try {
      List<String> authHeaders = serverWebExchange.getRequest()
        .getHeaders().get("Authorization");
      String token = authHeaders.get(0).split(" ")[1];
      authService.verifyToken(token);
      Object decoded = authService.decode(token);
      serverWebExchange.getAttributes().putIfAbsent("session", decoded);
      return webFilterChain.filter(serverWebExchange);
    } catch (NullPointerException e) {
      throw new TokenInvalidException(ErrorCode.EMPTY_TOKEN);
    }
  }
}
