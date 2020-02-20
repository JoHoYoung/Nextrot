package com.example.demo.service;

import com.example.demo.model.Singer;
import com.example.demo.model.Song;
import com.example.demo.model.SongAdmin;
import com.mongodb.client.result.UpdateResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
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
public class SongServiceImpl implements SongService{

  @Autowired
  ReactiveMongoTemplate reactiveMongoTemplate;

  public Flux<Song> findAllSongsSortBy(String field, int limit){
    List<AggregationOperation> aggregationOperations = new ArrayList<>();

    aggregationOperations.add(Aggregation.unwind("songs"));
    aggregationOperations.add(Aggregation.sort(Sort.Direction.DESC, "songs."+field));
    aggregationOperations.add(Aggregation.limit(limit));
    aggregationOperations.add(Aggregation.project()
      .andExpression("songs._id").as("_id")
      .andExpression("_id").as("singerId")
      .andExpression("name").as("singerName")
      .andExpression("songs.name").as("name")
      .andExpression("songs.lyrics").as("lyrics")
      .andExpression("songs.like").as("like")
      .andExpression("songs.view").as("view")
      .andExpression("songs.video").as("video")
      .andExpression("songs.createdAt").as("createdAt")
      .andExpression("songs.updatedAt").as("updatedAt"));
    Aggregation aggregation = Aggregation.newAggregation(aggregationOperations);
    return reactiveMongoTemplate.aggregate(aggregation, "singer", Song.class);

  }

  public Flux<Song> findAllSongsFromSingerId(String singerId){
    ArrayList<Criteria> orOperations = new ArrayList<>();
    orOperations.add(Criteria.where("_id").is(singerId));
    Criteria orCriteria = new Criteria();
    orCriteria.orOperator(orOperations.toArray(new Criteria[0]));

    List<AggregationOperation> aggregationOperations = new ArrayList<>();

    aggregationOperations.add(Aggregation.match(orCriteria));
    aggregationOperations.add(Aggregation.unwind("songs"));
    aggregationOperations.add(Aggregation.project()
      .andExpression("songs._id").as("_id")
      .andExpression("_id").as("singerId")
      .andExpression("name").as("singerName")
      .andExpression("songs.name").as("name")
      .andExpression("songs.lyrics").as("lyrics")
      .andExpression("songs.like").as("like")
      .andExpression("songs.view").as("view")
      .andExpression("songs.video").as("video")
      .andExpression("songs.createdAt").as("createdAt")
      .andExpression("songs.updatedAt").as("updatedAt"));
    Aggregation aggregation = Aggregation.newAggregation(aggregationOperations);
    return reactiveMongoTemplate.aggregate(aggregation, "singer", Song.class);
  }

  public Flux<Song> findAllSongsFromSinger(Singer singer) {
    ArrayList<Criteria> orOperations = new ArrayList<>();
    orOperations.add(Criteria.where("_id").is(singer.getId()));
    Criteria orCriteria = new Criteria();
    orCriteria.orOperator(orOperations.toArray(new Criteria[0]));

    List<AggregationOperation> aggregationOperations = new ArrayList<>();

    aggregationOperations.add(Aggregation.match(orCriteria));
    aggregationOperations.add(Aggregation.unwind("songs"));
    aggregationOperations.add(Aggregation.project()
      .andExpression("songs._id").as("_id")
      .andExpression("_id").as("singerId")
      .andExpression("name").as("singerName")
      .andExpression("songs.name").as("name")
      .andExpression("songs.lyrics").as("lyrics")
      .andExpression("songs.like").as("like")
      .andExpression("songs.view").as("view")
      .andExpression("songs.video").as("video")
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

    aggregationOperations.add(Aggregation.project()
      .andExpression("songs._id").as("_id")
      .andExpression("_id").as("singerId")
      .andExpression("name").as("singerName")
      .andExpression("songs.name").as("name")
      .andExpression("songs.lyrics").as("lyrics")
      .andExpression("songs.like").as("like")
      .andExpression("songs.view").as("view")
      .andExpression("songs.video").as("video")
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
      .andExpression("name").as("name")
      .andExpression("like").as("like")
      .andExpression("createdAt").as("createdAt")
      .andExpression("updatedAt").as("updatedAt")
      .andExpression("songs").as("songs")
    );

    aggregationOperations.add(Aggregation.match(Criteria.where("songs.updatedAt").gt(from)));
    aggregationOperations.add(Aggregation.project()
      .andExpression("songs._id").as("_id")
      .andExpression("_id").as("singerId")
      .andExpression("name").as("singerName")
      .andExpression("songs.name").as("name")
      .andExpression("songs.lyrics").as("lyrics")
      .andExpression("songs.like").as("like")
      .andExpression("songs.view").as("view")
      .andExpression("songs.createdAt").as("createdAt")
      .andExpression("songs.updatedAt").as("updatedAt"));

    Aggregation aggregation = Aggregation.newAggregation(aggregationOperations);
    return reactiveMongoTemplate.aggregate(aggregation, "singer", Song.class);
  }
  public Flux<Song> findAllSongsFromDateAndSingerId(Date from, String singerId) {
    ArrayList<Criteria> orOperations = new ArrayList<>();
    // All ids;
    orOperations.add(Criteria.where("_id").is(singerId));

    Criteria orCriteria = new Criteria();
    orCriteria.orOperator(orOperations.toArray(new Criteria[0]));

    List<AggregationOperation> aggregationOperations = new ArrayList<>();
    aggregationOperations.add(Aggregation.match(orCriteria));
    aggregationOperations.add(Aggregation.unwind("songs"));
    aggregationOperations.add(Aggregation.project().andExclude("songs.songs.video"));
    aggregationOperations.add(project()
      .andExpression("_id").as("_id")
      .andExpression("name").as("name")
      .andExpression("like").as("like")
      .andExpression("createdAt").as("createdAt")
      .andExpression("updatedAt").as("updatedAt")
      .andExpression("songs").as("songs")
    );

    aggregationOperations.add(Aggregation.match(Criteria.where("songs.updatedAt").gt(from)));
    aggregationOperations.add(Aggregation.project()
      .andExpression("songs._id").as("_id")
      .andExpression("_id").as("singerId")
      .andExpression("name").as("singerName")
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
      .andExpression("name").as("singerName")
      .andExpression("songs.name").as("name")
      .andExpression("songs.lyrics").as("lyrics")
      .andExpression("songs.like").as("like")
      .andExpression("songs.view").as("view")
      .andExpression("songs.createdAt").as("createdAt")
      .andExpression("songs.updatedAt").as("updatedAt"));
    Aggregation aggregation = Aggregation.newAggregation(aggregationOperations);
    return reactiveMongoTemplate.aggregate(aggregation, "singer", Song.class);
  }

  public Mono<UpdateResult> likeToSongById(String singerId, String songId){

    Update update = new Update();
    update.inc("songs.$.like", 1);
    return reactiveMongoTemplate.updateFirst(Query.query(Criteria.where("_id").is(singerId)
      .andOperator(Criteria.where("songs._id").is(songId))), update, "singer");
  }

  public Mono<UpdateResult> viewSongById(String singerId, String songId){
    Update update = new Update();
    update.inc("songs.$.view", 1);
    return reactiveMongoTemplate.updateFirst(Query.query(Criteria.where("_id").is(singerId)
    .andOperator(Criteria.where("songs._id").is(songId))), update, "singer");
  }

  public Flux<SongAdmin> getnullData(){

    ArrayList<Criteria> orOperations = new ArrayList<>();
    orOperations.add(Criteria.where("songs.video.key").is(""));
    orOperations.add(Criteria.where("songs.lyrics").is(""));
    Criteria orCriteria = new Criteria();
    orCriteria.orOperator(orOperations.toArray(new Criteria[0]));

    List<AggregationOperation> aggregationOperations = new ArrayList<>();
    aggregationOperations.add(Aggregation.unwind("songs"));
    aggregationOperations.add(Aggregation.unwind("songs.video"));
    aggregationOperations.add(Aggregation.match(orCriteria));
    aggregationOperations.add(Aggregation.project()
      .andExpression("songs._id").as("songId")
      .andExpression("songs.lyrics").as("lyrics")
      .andExpression("songs.name").as("songName")
      .andExpression("name").as("singerName")
      .andExpression("songs.video.key").as("key"));
    Aggregation aggregation = Aggregation.newAggregation(aggregationOperations);
    return reactiveMongoTemplate.aggregate(aggregation, "singer", SongAdmin.class);
  }

  public Mono<UpdateResult> updateField(String songId, String lyrics, String key){
    Update update = new Update();
    update.set("songs.$.lyrics",lyrics);
    update.set("songs.$.video.0.key",key);
    //return null;
    return reactiveMongoTemplate.updateFirst(Query.query(Criteria.where("songs._id").is(songId)), update, "singer");
  }

}
