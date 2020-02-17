package com.example.demo.controller;

import com.example.demo.ErrorCode;
import com.example.demo.exception.EmptyDataException;
import com.example.demo.exception.UnAuthorizedAccessException;
import com.example.demo.model.Like;
import com.example.demo.model.Session;
import com.example.demo.response.BaseResponse;
import com.example.demo.service.LikeService;
import com.example.demo.service.SingerService;
import com.example.demo.service.SongService;
import com.example.demo.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/like")
public class LikeController {

  @Autowired
  LikeService likeService;
  @Autowired
  SingerService singerService;
  @Autowired
  SongService songService;
  @Autowired
  VideoService videoService;


  @RequestMapping("/singer/{singerId}")
  public Mono<ResponseEntity<BaseResponse>> likeToSinger(@RequestAttribute("session") Session session,
                                                         @Valid @PathVariable("singerId") String singerId) {
    return likeService.findByTypeAndUidAndTargetId("singer", session.getUID(), singerId).map(el -> {
      throw new UnAuthorizedAccessException(ErrorCode.DUPLICATED_LIKE);
    })
      .switchIfEmpty(singerService.likeToSingerById(singerId).map(el -> {
        if (el.getMatchedCount() == 0) {
          throw new EmptyDataException(ErrorCode.INVALID_KEY);
        }
        return el;
      })
        .then(likeService.createLike(new Like("singer", session.getUID(), singerId))))
      .then(Mono.just(new ResponseEntity<>(new BaseResponse(200, "success"), HttpStatus.OK)));
  }

  @RequestMapping("/singer/{singerId}/song/{songId}")
  public Mono<ResponseEntity<BaseResponse>> likeToSong(@RequestAttribute("session") Session session,
                                                       @PathVariable("singerId") String singerId,
                                                       @PathVariable("songId") String songId) {
    return likeService.findByTypeAndUidAndTargetId("song", session.getUID(), songId).map(el -> {
      throw new UnAuthorizedAccessException(ErrorCode.DUPLICATED_LIKE);
    })
      .switchIfEmpty(songService.likeToSongById(singerId, songId).map(el -> {
        if (el.getMatchedCount() == 0) {
          throw new EmptyDataException(ErrorCode.INVALID_KEY);
        }
        return el;
      })
        .then(likeService.createLike(new Like("song", session.getUID(), songId))))
      .then(Mono.just(new ResponseEntity<>(new BaseResponse(200, "success"), HttpStatus.OK)));
  }

  @RequestMapping("/singer/{singerId}/song/{songId}/video/{videoId}")
  public Mono<ResponseEntity<BaseResponse>> likeToVideo(@RequestAttribute("session") Session session,
                                                        @PathVariable("singerId") String singerId,
                                                        @PathVariable("songId") String songId,
                                                        @PathVariable("videoId") String videoId) {

    return likeService.findByTypeAndUidAndTargetId("video", session.getUID(), videoId).map(el -> {
      System.out.println(el);
      throw new UnAuthorizedAccessException(ErrorCode.DUPLICATED_LIKE);
    })
      .switchIfEmpty(videoService.likeVideoById(singerId, songId, videoId).map(el -> {
        if (el.getMatchedCount() == 0) {
          throw new EmptyDataException(ErrorCode.INVALID_KEY);
        }
        return el;
      })
        .then(likeService.createLike(new Like("video", session.getUID(), videoId))))
      .then(Mono.just(new ResponseEntity<>(new BaseResponse(200, "success"), HttpStatus.OK)));
  }
}
