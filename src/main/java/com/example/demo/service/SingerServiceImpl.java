package com.example.demo.service;

import com.example.demo.model.Singer;
import com.example.demo.model.Song;
import com.example.demo.model.Video;
import com.example.demo.repository.SingerRespository;
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

import static org.springframework.data.mongodb.core.aggregation.Aggregation.bind;
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


  public Flux<Singer> findAllSongsFromSinger(Singer singer) {
    ArrayList<Criteria> orOperations = new ArrayList<>();
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

    aggregationOperations.add(Aggregation.group("_id")
      .first("name").as("name")
      .first("like").as("like")
      .first("createdAt").as("createdAt")
      .first("updatedAt").as("updatedAt")
      .addToSet("songs").as("songs"));
    Aggregation aggregation = Aggregation.newAggregation(aggregationOperations);
    return reactiveMongoTemplate.aggregate(aggregation, "singer", Singer.class);
  }

  public Flux<Singer> findAllSongsFromSingers(List<Singer> singers) {
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
    aggregationOperations.add(project()
      .andExpression("_id").as("_id")
      .andExpression("$name").as("name")
      .andExpression("like").as("like")
      .andExpression("createdAt").as("createdAt")
      .andExpression("updatedAt").as("updatedAt")
      .andExpression("songs").as("songs")
    );
    aggregationOperations.add(Aggregation.group("_id")
      .first("name").as("name")
      .first("like").as("like")
      .first("createdAt").as("createdAt")
      .first("updatedAt").as("updatedAt")
      .addToSet("songs").as("songs"));
    Aggregation aggregation = Aggregation.newAggregation(aggregationOperations);
    return reactiveMongoTemplate.aggregate(aggregation, "singer", Singer.class);
  }


  public Flux<Singer> findAllSongsFromDateAndSinger(Date from, Singer singer) {
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
    aggregationOperations.add(Aggregation.group("_id")
      .first("name").as("name")
      .first("like").as("like")
      .first("createdAt").as("createdAt")
      .first("updatedAt").as("updatedAt")
      .addToSet("songs").as("songs"));

    Aggregation aggregation = Aggregation.newAggregation(aggregationOperations);
    return reactiveMongoTemplate.aggregate(aggregation, "singer", Singer.class);
  }

  public Flux<Singer> findAllSongsFromDateAndSingers(Date from, List<Singer> singers) {

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
    aggregationOperations.add(project()
      .andExpression("_id").as("_id")
      .andExpression("$name").as("name")
      .andExpression("like").as("like")
      .andExpression("createdAt").as("createdAt")
      .andExpression("updatedAt").as("updatedAt")
      .andExpression("songs").as("songs")
    );

    aggregationOperations.add(Aggregation.match(Criteria.where("songs.updatedAt").gt(from)));
    aggregationOperations.add(Aggregation.group("_id")
      .first("name").as("name")
      .first("like").as("like")
      .first("createdAt").as("createdAt")
      .first("updatedAt").as("updatedAt")
      .addToSet("songs").as("songs"));
    Aggregation aggregation = Aggregation.newAggregation(aggregationOperations);
    return reactiveMongoTemplate.aggregate(aggregation, "singer", Singer.class);
  }


  public Flux<Singer> findAllVideoFromDateAndSongs(Date from, List<Singer> singers) {

    ArrayList<Criteria> singerOrOperator = new ArrayList<>();
    ArrayList<Criteria> songOrOperator = new ArrayList<>();

    // All ids;
    for (Singer singer : singers) {
      singerOrOperator.add(Criteria.where("_id").is(singer.getId()));
      for (Song song : singer.getSongs()) {
        songOrOperator.add(Criteria.where("songs._id").is(song.getId()));
      }
    }

    Criteria singerOrCriteria = new Criteria();
    singerOrCriteria.orOperator(singerOrOperator.toArray(new Criteria[0]));

    Criteria songOrCriteria = new Criteria();
    songOrCriteria.orOperator(songOrOperator.toArray(new Criteria[0]));

    List<AggregationOperation> aggregationOperations = new ArrayList<>();
    aggregationOperations.add(Aggregation.match(singerOrCriteria));
    aggregationOperations.add(Aggregation.unwind("songs"));
    aggregationOperations.add(Aggregation.match(songOrCriteria));
    aggregationOperations.add(Aggregation.unwind("songs.video"));
    aggregationOperations.add(Aggregation.match(Criteria.where("songs.video.updatedAt").gt(from)));
    aggregationOperations.add(Aggregation.group("songs._id")
      .first("$$ROOT").as("root")
      .push("songs.video").as("video"));
    aggregationOperations.add(Aggregation.project()
      .andExpression("root").as("root")
      .andExpression("root.name").as("name")
      .andExpression("root.like").as("like")
      .and("songs").nested(bind("id", "root.songs.id")
        .and("name", "root.songs.name")
        .and("lyrics", "root.songs.lyrics")
        .and("like", "root.songs.like")
        .and("view", "root.songs.view")
        .and("video", "video")));

    aggregationOperations.add(Aggregation.group("$root._id")
      .first("root.name").as("name")
      .first("root.like").as("like")
      .push("songs").as("songs")
      .first("root.createdAt").as("createdAt")
      .first("root.updatedAt").as("updatedAt"));
    Aggregation aggregation = Aggregation.newAggregation(aggregationOperations);

    return reactiveMongoTemplate.aggregate(aggregation, "singer", Singer.class);
  }

  public Flux<Singer> findAllVideoBySongId(String songId) {

    List<AggregationOperation> aggregationOperations = new ArrayList<>();

    aggregationOperations.add(Aggregation.unwind("songs"));
    aggregationOperations.add(Aggregation.unwind("songs.video"));
    aggregationOperations.add(Aggregation.match(Criteria.where("songs._id").is(songId)));
    aggregationOperations.add(Aggregation.group("songs._id")
      .first("$$ROOT").as("root")
      .push("songs.video").as("video"));
    aggregationOperations.add(Aggregation.project()
      .andExpression("root").as("root")
      .andExpression("root.name").as("name")
      .andExpression("root.like").as("like")
      .and("songs").nested(bind("id", "root.songs.id")
        .and("name", "root.songs.name")
        .and("lyrics", "root.songs.lyrics")
        .and("like", "root.songs.like")
        .and("view", "root.songs.view")
        .and("video", "video")));

    aggregationOperations.add(Aggregation.group("$root._id")
      .first("root.name").as("name")
      .first("root.like").as("like")
      .push("songs").as("songs")
      .first("root.createdAt").as("createdAt")
      .first("root.updatedAt").as("updatedAt"));
    Aggregation aggregation = Aggregation.newAggregation(aggregationOperations);
    return reactiveMongoTemplate.aggregate(aggregation, "singer", Singer.class);
  }

  public Flux<Singer> findAllVideoBySongIdAndFromDate(Date from, String songId) {
    List<AggregationOperation> aggregationOperations = new ArrayList<>();

    aggregationOperations.add(Aggregation.unwind("songs"));
    aggregationOperations.add(Aggregation.match(Criteria.where("songs._id").is(songId)));
    aggregationOperations.add(Aggregation.unwind("songs.video"));
    aggregationOperations.add(Aggregation.match(Criteria.where("songs.video.updatedAt").gt(from)));
    aggregationOperations.add(Aggregation.group("songs._id")
      .first("$$ROOT").as("root")
      .push("songs.video").as("video"));
    aggregationOperations.add(Aggregation.project()
      .andExpression("root").as("root")
      .andExpression("root.name").as("name")
      .andExpression("root.like").as("like")
      .and("songs").nested(bind("id", "root.songs.id")
        .and("name", "root.songs.name")
        .and("lyrics", "root.songs.lyrics")
        .and("like", "root.songs.like")
        .and("view", "root.songs.view")
        .and("video", "video")));

    aggregationOperations.add(Aggregation.group("$root._id")
      .first("root.name").as("name")
      .first("root.like").as("like")
      .push("songs").as("songs")
      .first("root.createdAt").as("createdAt")
      .first("root.updatedAt").as("updatedAt"));
    Aggregation aggregation = Aggregation.newAggregation(aggregationOperations);

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
    update.addToSet("songs.$.video", video);
    Query query = new Query(Criteria.where("_id").is(singerId).andOperator(Criteria.where("songs._id").is(songId)));

    return reactiveMongoTemplate.updateFirst(query, update, "singer");
  }

}
