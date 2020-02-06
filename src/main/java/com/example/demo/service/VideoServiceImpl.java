package com.example.demo.service;

import com.example.demo.model.Singer;
import com.example.demo.model.Song;
import com.example.demo.model.Video;
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


@Service
public class VideoServiceImpl implements VideoService {

  @Autowired
  ReactiveMongoTemplate reactiveMongoTemplate;

  public Flux<Video> findAllVideoFromDateAndSongs(Date from, List<Singer> singers, List<Song> songs) {

    ArrayList<Criteria> singerOrOperator = new ArrayList<>();
    ArrayList<Criteria> songOrOperator = new ArrayList<>();

    // All ids;
    for (Singer singer : singers) {
      singerOrOperator.add(Criteria.where("_id").is(singer.getId()));
      for (Song song : songs) {
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
    aggregationOperations.add(Aggregation.project()
      .andExpression("songs.video._id").as("_id")
      .andExpression("songs.video.like").as("like")
      .andExpression("songs.video.view").as("view")
      .andExpression("songs.video.createdAt").as("createdAt")
      .andExpression("songs.video.updatedAt").as("updatedAt")
    .andExpression("songs._id").as("songId"));
    Aggregation aggregation = Aggregation.newAggregation(aggregationOperations);

    return reactiveMongoTemplate.aggregate(aggregation, "singer", Video.class);
  }

  public Flux<Video> findAllVideoBySongId(String songId) {

    List<AggregationOperation> aggregationOperations = new ArrayList<>();

    aggregationOperations.add(Aggregation.unwind("songs"));
    aggregationOperations.add(Aggregation.unwind("songs.video"));
    aggregationOperations.add(Aggregation.match(Criteria.where("songs._id").is(songId)));
    aggregationOperations.add(Aggregation.project()
      .andExpression("songs.video._id").as("_id")
      .andExpression("songs.video.like").as("like")
      .andExpression("songs.video.view").as("view")
      .andExpression("songs.video.createdAt").as("createdAt")
      .andExpression("songs.video.updatedAt").as("updatedAt")
      .andExpression("songs._id").as("songId"));
    Aggregation aggregation = Aggregation.newAggregation(aggregationOperations);
    return reactiveMongoTemplate.aggregate(aggregation, "singer", Video.class);
  }

  public Flux<Video> findAllVideoBySongIdAndFromDate(Date from, String songId) {
    List<AggregationOperation> aggregationOperations = new ArrayList<>();

    aggregationOperations.add(Aggregation.unwind("songs"));
    aggregationOperations.add(Aggregation.match(Criteria.where("songs._id").is(songId)));
    aggregationOperations.add(Aggregation.unwind("songs.video"));
    aggregationOperations.add(Aggregation.match(Criteria.where("songs.video.updatedAt").gt(from)));
    aggregationOperations.add(Aggregation.project()
      .andExpression("songs.video._id").as("_id")
      .andExpression("songs.video.like").as("like")
      .andExpression("songs.video.view").as("view")
      .andExpression("songs.video.createdAt").as("createdAt")
      .andExpression("songs.video.updatedAt").as("updatedAt")
      .andExpression("songs._id").as("songId"));
    Aggregation aggregation = Aggregation.newAggregation(aggregationOperations);
    return reactiveMongoTemplate.aggregate(aggregation, "singer", Video.class);

  }

  public Mono<UpdateResult> likeVideoById(String singerId, String songId, String videoId){
    Update update = new Update();
    update.inc("songs.$[i].video.$[j].like", 1);
    update.filterArray("i._id", songId);
    update.filterArray("j._id", videoId);

    return reactiveMongoTemplate.updateFirst(Query.query(Criteria.where("_id").is(singerId)
    .andOperator(Criteria.where("songs._id").is(songId))
    .andOperator(Criteria.where("songs.video._id").is(videoId))), update, "singer");
  }

}
