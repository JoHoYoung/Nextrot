package com.example.demo.service;

import com.example.demo.model.Like;
import com.example.demo.repository.LikeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class LikeServiceImpl implements LikeService{

  @Autowired
  LikeRepository likeRepository;

  public Mono<Like> createLike(Like like){
    return likeRepository.save(like);
  }

  public Flux<Like> findByTypeAndUid(String type, String UID){
    return likeRepository.findByTypeAndUid(type, UID);
  }

}
