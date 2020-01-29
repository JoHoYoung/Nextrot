package com.example.demo.repository;

import com.example.demo.model.Test;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface TestRepository extends ReactiveMongoRepository<Test, Integer> {

  @Query("{'name':?0}")
  Flux<Test> findByName(String name);
}
