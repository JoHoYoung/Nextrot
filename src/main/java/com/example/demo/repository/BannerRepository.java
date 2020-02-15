package com.example.demo.repository;

import com.example.demo.model.Banner;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface BannerRepository extends ReactiveMongoRepository<Banner, String> {

  Mono<Banner> findById();

  @Query("{}")
  Flux<Banner> findAllBanner();

  @Query("{'state' : 'C'}")
  Flux<Banner> findAllActiveBanner();

}
