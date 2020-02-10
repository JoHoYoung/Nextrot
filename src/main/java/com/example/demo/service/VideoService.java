package com.example.demo.service;

import com.example.demo.model.Singer;
import com.example.demo.model.Song;
import com.example.demo.model.Video;
import com.mongodb.client.result.UpdateResult;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Date;
import java.util.List;

public interface VideoService {

  Flux<Video> findAllVideoFromDateAndSongs(Date from, List<Singer> singers, List<Song> songs);

  Flux<Video> findAllVideoBySongId(String songId);

  Flux<Video> findAllVideoBySongIdAndFromDate(Date from, String songId);

  Mono<UpdateResult> likeVideoById(String singerId, String songId, String videoId);

  Mono<UpdateResult> viewSongById(String singerId, String songId,String videoId);

}
