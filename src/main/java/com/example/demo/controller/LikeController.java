package com.example.demo.controller;

import com.example.demo.ErrorCode;
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
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.naming.OperationNotSupportedException;
import javax.xml.ws.Response;

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
  public Mono<ResponseEntity<BaseResponse>> likeToSinger(@RequestAttribute("session")Session session, @PathVariable("singerId")String singerId){
    return likeService.findByTypeAndUid("singer", session.getUID()).map(el -> {
      throw new UnAuthorizedAccessException(ErrorCode.DUPLICATED_LIKE);
    }).switchIfEmpty(Flux.merge(likeService.createLike(new Like("singer", session.getUID())),
      singerService.likeToSingerById(singerId)
      )).collectList().map(el -> {
        final BaseResponse response = new BaseResponse(200, "success");
        return new ResponseEntity<>(response, HttpStatus.OK);
    });
  }

  @RequestMapping("/singer/{sigerId}/song/{songId}")
  public Mono<ResponseEntity<BaseResponse>> likeToSong(@RequestAttribute("session")Session session,
                                                       @PathVariable("singerId")String singerId, @PathVariable("songId")String songId){
    return likeService.findByTypeAndUid("song", session.getUID()).map(el -> {
      throw new UnAuthorizedAccessException(ErrorCode.DUPLICATED_LIKE);
    }).switchIfEmpty(Flux.merge(likeService.createLike(new Like("song", session.getUID())), songService.likeToSongById(singerId, songId))
      .collectList().map(el -> {
        final BaseResponse response = new BaseResponse(200, "success");
        return new ResponseEntity<>(response, HttpStatus.OK);
      });
  }

  @RequestMapping("/singer/{sigerId}/song/{songId}/video/{videoId}")
  public Mono<ResponseEntity<BaseResponse>> likeToVideo(@RequestAttribute("session")Session session,
                                                        @PathVariable("singerId")String singerId,
                                                        @PathVariable("songId")String songId,
                                                        @PathVariable("videoId")String videoId){
    return likeService.findByTypeAndUid("video", session.getUID()).map(el -> {
      throw new UnAuthorizedAccessException(ErrorCode.DUPLICATED_LIKE);
    }).switchIfEmpty(Flux.merge(likeService.createLike(new Like("video", session.getUID())),
      videoService.likeVideoById(singerId, songId, videoId)))
      .collectList().map(el -> {
        final BaseResponse response = new BaseResponse(200, "success");
        return new ResponseEntity<>(response, HttpStatus.OK);
      });
  }
}
