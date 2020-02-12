package com.example.demo.service;

import com.example.demo.model.Like;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface LikeService {

  Flux<Like> findByTypeAndUidAndTargetId(String type, String UID, String targetId);

  Mono<Like> createLike(Like like);
}
