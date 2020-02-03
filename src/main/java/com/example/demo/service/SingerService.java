package com.example.demo.service;

import com.example.demo.model.Singer;
import com.example.demo.model.Song;
import com.example.demo.model.Video;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Date;
import java.util.List;

public interface SingerService {

  Mono<Singer> createSinger(Singer singer);

  Mono<Singer> findOneById(String id);

  Flux<Singer> findAllData();

  Flux<Singer> findAllSingers();

  Flux<Singer> findSingersByName(String name);

  Flux<Singer> findAllSongsFromSingers(List<Singer> singers);

  Flux<Singer> findAllNewSinger(Date from);

  Flux<Singer> findAllSongsFromSinger(Singer singers);

  Flux<Singer> findAllSingersFromDate(Date from);

  Flux<Singer> findAllSongsFromDateAndSingers(Date from, List<Singer> singers);

  Flux<Singer> findAllSongsFromDateAndSinger(Date from, Singer singers);

  Flux<Singer> findAllVideoFromDateAndSongs(Date from, List<Singer> songs);

  Flux<Singer> findAllVideoBySongId(String songId);

  Flux<Singer> findAllVideoBySongIdAndFromDate(Date from, String songId);

  Mono<UpdateResult> insertSongToSingerByName(String name, Song song);

  Mono<UpdateResult> insertSongToSingerById(String id, Song song);

  Mono<UpdateResult> insertSongsToSingerByName(String name, List<Song> songs);

  Mono<UpdateResult> insertSongsToSingerById(String id, List<Song> songs);

  Mono<UpdateResult> insertVideoToSong(String singerId, String songId, Video video);

  Mono<Void> deleteSingerById(String id);

  Mono<DeleteResult> deleteSingerByName(String name);


}
