package com.example.demo.controller;

import com.example.demo.response.BaseResponse;
import com.example.demo.service.LikeService;
import com.example.demo.service.SingerService;
import com.example.demo.service.SongService;
import com.example.demo.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/view")
public class ViewController {

  @Autowired
  LikeService likeService;
  @Autowired
  SingerService singerService;
  @Autowired
  SongService songService;
  @Autowired
  VideoService videoService;


  @RequestMapping("/singer/{singerId}/song/{songId}")
  public Mono<ResponseEntity<BaseResponse>> likeToSong(@PathVariable("singerId")String singerId,
                                                       @PathVariable("songId")String songId){
    return songService.viewSongById(singerId, songId).map(el -> {
      final BaseResponse response = new BaseResponse(200, "success");
      return new ResponseEntity<>(response, HttpStatus.OK);
    });
  }

  @RequestMapping("/singer/{singerId}/song/{songId}/video/{videoId}")
  public Mono<ResponseEntity<BaseResponse>> likeToVideo(@PathVariable("singerId")String singerId,
                                                        @PathVariable("songId")String songId,
                                                        @PathVariable("videoId")String videoId){
    return Flux.merge(songService.viewSongById(singerId, songId), videoService.viewSongById(singerId, songId, videoId))
      .collectList().map(el -> {
        final BaseResponse response = new BaseResponse(200, "success");
        return new ResponseEntity<>(response, HttpStatus.OK);
      });
  }
}
