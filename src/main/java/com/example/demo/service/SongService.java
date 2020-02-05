package com.example.demo.service;

import com.example.demo.model.Singer;
import com.example.demo.model.Song;
import reactor.core.publisher.Flux;

import java.util.Date;
import java.util.List;

public interface SongService {
  Flux<Song> findAllSongsFromDateAndSingers(Date from, List<Singer> singers);

  Flux<Song> findAllSongsFromDateAndSinger(Date from, Singer singers);

  Flux<Song> findAllSongsFromSingers(List<Singer> singers);

  Flux<Song> findAllSongsFromSinger(Singer singers);


}
