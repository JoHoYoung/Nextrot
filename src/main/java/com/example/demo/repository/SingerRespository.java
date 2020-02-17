package com.example.demo.repository;

import com.example.demo.model.Singer;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Range;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
@Component
public interface SingerRespository extends ReactiveMongoRepository<Singer, String>{

  @Query("{}")
  Flux<Singer> findAll();

  @Query("{'name' : ?0 }")
  Flux<Singer> findByName(String name);

  @Override
  Mono<Void> deleteById(String s);

}
