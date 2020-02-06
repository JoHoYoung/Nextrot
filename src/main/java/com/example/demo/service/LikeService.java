package com.example.demo.service;

import com.example.demo.model.Like;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface LikeService {

  Flux<Like> findByTypeAndUid(String type, String UID);

  Mono<Like> createLike(Like like);
}
