package com.example.demo.service;


import com.example.demo.model.Banner;
import com.example.demo.model.Song;
import com.example.demo.repository.BannerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@Service
public class BannerServiceImpl implements BannerService{

  @Autowired
  BannerRepository bannerRepository;

  @Autowired
  ReactiveMongoTemplate reactiveMongoTemplate;

  public Flux<Banner> findAllAciveBanner(){
    List<AggregationOperation> aggregationOperations = new ArrayList<>();

    aggregationOperations.add(Aggregation.project().andExclude("data"));
    aggregationOperations.add(Aggregation.match(Criteria.where("state").is('C')));
    Aggregation aggregation = Aggregation.newAggregation(aggregationOperations);
    return reactiveMongoTemplate.aggregate(aggregation,"banner", Banner.class);

  }

  public Flux<Banner> findBannerById(String bannerId){
    List<AggregationOperation> aggregationOperations = new ArrayList<>();

    aggregationOperations.add(Aggregation.match(Criteria.where("_id").is(bannerId)));
    Aggregation aggregation = Aggregation.newAggregation(aggregationOperations);
    return reactiveMongoTemplate.aggregate(aggregation, "banner", Banner.class);
  }

}
