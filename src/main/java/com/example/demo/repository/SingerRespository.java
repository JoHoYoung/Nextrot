package com.example.demo.repository;

import com.example.demo.model.Singer;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface SingerRespository extends ReactiveMongoRepository<Singer, String> {

  @Query("{'name' : ?0 }")
  Flux<Singer> findByName(String name);

  @Override
  Mono<Void> deleteById(String s);

}
