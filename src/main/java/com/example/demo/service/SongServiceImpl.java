package com.example.demo.service;

import com.example.demo.model.Singer;
import com.example.demo.model.Song;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.project;

@Service
public class SongServiceImpl implements SongService{

  @Autowired
  ReactiveMongoTemplate reactiveMongoTemplate;

  public Flux<Song> findAllSongsFromSinger(Singer singer) {
    ArrayList<Criteria> orOperations = new ArrayList<>();
    orOperations.add(Criteria.where("_id").is(singer.getId()));
    Criteria orCriteria = new Criteria();
    orCriteria.orOperator(orOperations.toArray(new Criteria[0]));

    List<AggregationOperation> aggregationOperations = new ArrayList<>();

    aggregationOperations.add(Aggregation.match(orCriteria));
    aggregationOperations.add(Aggregation.unwind("songs"));
    aggregationOperations.add(Aggregation.project().andExclude("songs.songs.video"));
    aggregationOperations.add(Aggregation.project()
      .andExpression("songs._id").as("_id")
      .andExpression("_id").as("singerId")
      .andExpression("songs.name").as("name")
      .andExpression("songs.lyrics").as("lyrics")
      .andExpression("songs.like").as("like")
      .andExpression("songs.view").as("view")
      .andExpression("songs.createdAt").as("createdAt")
      .andExpression("songs.updatedAt").as("updatedAt"));
    Aggregation aggregation = Aggregation.newAggregation(aggregationOperations);
    return reactiveMongoTemplate.aggregate(aggregation, "singer", Song.class);
  }

  public Flux<Song> findAllSongsFromSingers(List<Singer> singers) {
    ArrayList<Criteria> orOperations = new ArrayList<>();

    // All ids;
    for (Singer singer : singers) {
      orOperations.add(Criteria.where("_id").is(singer.getId()));
    }

    Criteria orCriteria = new Criteria();
    orCriteria.orOperator(orOperations.toArray(new Criteria[0]));

    List<AggregationOperation> aggregationOperations = new ArrayList<>();
    aggregationOperations.add(Aggregation.match(orCriteria));
    aggregationOperations.add(Aggregation.unwind("songs"));
    aggregationOperations.add(Aggregation.project().andExclude("songs.songs.video"));

    aggregationOperations.add(Aggregation.project()
      .andExpression("songs._id").as("_id")
      .andExpression("_id").as("singerId")
      .andExpression("songs.name").as("name")
      .andExpression("songs.lyrics").as("lyrics")
      .andExpression("songs.like").as("like")
      .andExpression("songs.view").as("view")
      .andExpression("songs.createdAt").as("createdAt")
      .andExpression("songs.updatedAt").as("updatedAt")); Aggregation aggregation = Aggregation.newAggregation(aggregationOperations);
    return reactiveMongoTemplate.aggregate(aggregation, "singer", Song.class);
  }


  public Flux<Song> findAllSongsFromDateAndSinger(Date from, Singer singer) {
    ArrayList<Criteria> orOperations = new ArrayList<>();
    // All ids;
    orOperations.add(Criteria.where("_id").is(singer.getId()));

    Criteria orCriteria = new Criteria();
    orCriteria.orOperator(orOperations.toArray(new Criteria[0]));

    List<AggregationOperation> aggregationOperations = new ArrayList<>();
    aggregationOperations.add(Aggregation.match(orCriteria));
    aggregationOperations.add(Aggregation.unwind("songs"));
    aggregationOperations.add(Aggregation.project().andExclude("songs.songs.video"));
    aggregationOperations.add(project()
      .andExpression("_id").as("_id")
      .andExpression("$name").as("name")
      .andExpression("like").as("like")
      .andExpression("createdAt").as("createdAt")
      .andExpression("updatedAt").as("updatedAt")
      .andExpression("songs").as("songs")
    );

    aggregationOperations.add(Aggregation.match(Criteria.where("songs.updatedAt").gt(from)));
    aggregationOperations.add(Aggregation.project()
      .andExpression("songs._id").as("_id")
      .andExpression("_id").as("singerId")
      .andExpression("songs.name").as("name")
      .andExpression("songs.lyrics").as("lyrics")
      .andExpression("songs.like").as("like")
      .andExpression("songs.view").as("view")
      .andExpression("songs.createdAt").as("createdAt")
      .andExpression("songs.updatedAt").as("updatedAt"));

    Aggregation aggregation = Aggregation.newAggregation(aggregationOperations);
    return reactiveMongoTemplate.aggregate(aggregation, "singer", Song.class);
  }

  public Flux<Song> findAllSongsFromDateAndSingers(Date from, List<Singer> singers) {

    ArrayList<Criteria> orOperations = new ArrayList<>();

    // All ids;
    for (Singer singer : singers) {
      orOperations.add(Criteria.where("_id").is(singer.getId()));
    }

    Criteria orCriteria = new Criteria();
    orCriteria.orOperator(orOperations.toArray(new Criteria[0]));

    List<AggregationOperation> aggregationOperations = new ArrayList<>();
    aggregationOperations.add(Aggregation.match(orCriteria));
    aggregationOperations.add(Aggregation.unwind("songs"));
    aggregationOperations.add(Aggregation.project().andExclude("songs.songs.video"));

    aggregationOperations.add(Aggregation.match(Criteria.where("songs.updatedAt").gt(from)));
    aggregationOperations.add(Aggregation.project()
      .andExpression("songs._id").as("_id")
      .andExpression("_id").as("singerId")
      .andExpression("songs.name").as("name")
      .andExpression("songs.lyrics").as("lyrics")
      .andExpression("songs.like").as("like")
      .andExpression("songs.view").as("view")
      .andExpression("songs.createdAt").as("createdAt")
      .andExpression("songs.updatedAt").as("updatedAt"));
    Aggregation aggregation = Aggregation.newAggregation(aggregationOperations);
    return reactiveMongoTemplate.aggregate(aggregation, "singer", Song.class);
  }

}
