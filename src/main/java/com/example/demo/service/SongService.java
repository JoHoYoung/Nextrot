package com.example.demo.service;

import com.example.demo.model.Singer;
import com.example.demo.model.Song;
import com.mongodb.client.result.UpdateResult;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Date;
import java.util.List;

public interface SongService {
  Flux<Song> findAllSongsFromDateAndSingers(Date from, List<Singer> singers);

  Flux<Song> findAllSongsFromDateAndSinger(Date from, Singer singers);

  Flux<Song> findAllSongsFromSingers(List<Singer> singers);

  Flux<Song> findAllSongsFromSinger(Singer singers);

  Mono<UpdateResult> likeToSongById(String singerId, String songId);


}
