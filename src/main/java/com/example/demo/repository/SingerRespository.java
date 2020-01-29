package com.example.demo.repository;

import com.example.demo.model.Singer;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface SingerRespository extends ReactiveMongoRepository<Singer, Integer> {
 // Mono<Singer> insert(Singer singer);
}
