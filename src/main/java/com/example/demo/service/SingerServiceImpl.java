package com.example.demo.service;

import com.example.demo.model.Singer;
import com.example.demo.model.Song;
import com.example.demo.model.Video;
import com.example.demo.repository.SingerRespository;
import com.example.demo.model.Like;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.project;

@Service
public class SingerServiceImpl implements SingerService {
  @Autowired
  SingerRespository singerRespository;
  @Autowired
  ReactiveMongoTemplate reactiveMongoTemplate;


  public Flux<Singer> findAllData() {
    return singerRespository.findAll();
  }

  public Mono<Singer> createSinger(Singer singer) {
    return singerRespository.save(singer);
  }

  public Mono<Singer> findOneById(String id) {
    return singerRespository.findById(id);
  }

  public Flux<Singer> findSingersByName(String name) {
    return singerRespository.findByName(name);
  }

  public Flux<Singer> findAllNewSinger(Date from) {
    List<AggregationOperation> aggregationOperations = new ArrayList<>();

    aggregationOperations.add(Aggregation.match(Criteria.where("createdAt").gt(from)));
    aggregationOperations.add(project().andExclude("songs"));

    Aggregation aggregation = Aggregation.
      newAggregation(aggregationOperations);
    return reactiveMongoTemplate.aggregate(aggregation, "singer", Singer.class);
  }

  // Get Singer Exclude Songs
  public Flux<Singer> findAllSingersFromDate(Date from) {

    List<AggregationOperation> aggregationOperations = new ArrayList<>();

    aggregationOperations.add(Aggregation.match(Criteria.where("updatedAt").gt(from)));
    aggregationOperations.add(project().andExclude("songs"));

    Aggregation aggregation = Aggregation.
      newAggregation(aggregationOperations);

    return reactiveMongoTemplate.aggregate(aggregation, "singer", Singer.class);
  }

  // Get Singer Exclude Songs
  public Flux<Singer> findAllSingers() {

    List<AggregationOperation> aggregationOperations = new ArrayList<>();

    aggregationOperations.add(project().andExclude("songs"));

    Aggregation aggregation = Aggregation.
      newAggregation(aggregationOperations);

    return reactiveMongoTemplate.aggregate(aggregation, "singer", Singer.class);
  }


  public Mono<UpdateResult> insertSongToSingerByName(String name, Song song) {

    Update update = new Update();
    update.addToSet("songs", song);
    update.currentDate("updatedAt");

    return reactiveMongoTemplate.updateFirst(Query.query(Criteria.where("name").is(name)),
      update, "singer");
  }

  public Mono<UpdateResult> insertSongToSingerById(String id, Song song) {

    Update update = new Update();
    update.addToSet("songs", song);
    song.setSingerId(id);
    update.currentDate("updatedAt");

    return reactiveMongoTemplate.updateFirst(Query.query(Criteria.where("_id").is(id)),
      update, "singer");
  }

  public Mono<UpdateResult> insertSongsToSingerByName(String name, List<Song> songs) {

    Update update = new Update();
    update.currentDate("updatedAt");

    for (Song song : songs) {
      update.addToSet("songs", song);
    }

    return reactiveMongoTemplate.updateFirst(Query.query(Criteria.where("name").is(name)),
      update, "singer");
  }

  public Mono<UpdateResult> insertSongsToSingerById(String id, List<Song> songs) {

    Update update = new Update();
    update.currentDate("updatedAt");

    for (Song song : songs) {
      song.setSingerId(id);
      update.addToSet("songs", song);
    }
    return reactiveMongoTemplate.updateFirst(Query.query(Criteria.where("_id").is(id)),
      update, "singer");
  }

  public Mono<Void> deleteSingerById(String id) {
    return singerRespository.deleteById(id);
  }

  public Mono<DeleteResult> deleteSingerByName(String name) {
    Query query = new Query();
    query.addCriteria(Criteria.where("name").is(name));
    return reactiveMongoTemplate.remove(query, "singer");
  }

  public Mono<UpdateResult> insertVideoToSong(String singerId, String songId, Video video) {

    Update update = new Update();

    update.currentDate("updatedAt");
    update.currentDate("songs.$.updatedAt");
    video.setSongId(songId);
    update.addToSet("songs.$.video", video);
    Query query = new Query(Criteria.where("_id").is(singerId).andOperator(Criteria.where("songs._id").is(songId)));
    return reactiveMongoTemplate.updateFirst(query, update, "singer");
  }

  public Mono<UpdateResult> likeToSingerById(String singerId){
    Update update =  new Update();
    update.inc("like", 1);
    return reactiveMongoTemplate.updateFirst(Query.query(Criteria.where("_id").is(singerId)),update, "singer");
  }


}
