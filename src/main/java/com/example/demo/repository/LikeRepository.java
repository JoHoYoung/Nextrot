package com.example.demo.repository;

import com.example.demo.model.Like;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface LikeRepository extends ReactiveMongoRepository<Like, String> {

  @Query("{$and : [{'type' : ?0}, {'uid' : ?1}]")
  Flux<Like> findByTypeAndUid(String uid, String type);

}
