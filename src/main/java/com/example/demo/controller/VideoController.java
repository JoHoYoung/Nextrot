package com.example.demo.controller;

import com.example.demo.ErrorCode;
import com.example.demo.exception.EmptyDataException;
import com.example.demo.model.Song;
import com.example.demo.response.BaseResponse;
import com.example.demo.response.DataListResponse;
import com.example.demo.service.SingerService;
import com.example.demo.service.SongService;
import com.example.demo.service.VideoService;
import com.example.demo.util.DateHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/v1/videos")
public class VideoController {

  @Autowired
  SingerService singerService;
  @Autowired
  SongService songService;

  @Autowired
  VideoService videoService;

  @Autowired
  DateHelper dateHelper;

  //특정 노래의 비디오 리스트
  @GetMapping("")
  public Mono<ResponseEntity<BaseResponse>> getVideoFromSong(@Valid @RequestParam("songId") String songId) {
    return videoService.findAllVideoBySongId(songId)
      .switchIfEmpty(Mono.error(new EmptyDataException(ErrorCode.INVALID_KEY)))
      .collectList()
      .map(data -> {
        final BaseResponse response = new DataListResponse<>(200, "success", data);
        return new ResponseEntity<>(response, HttpStatus.OK);
      });
  }

  // 갱신이 생긴 모든 비디오 리스트
  @GetMapping("/updated")
  public Mono<ResponseEntity<BaseResponse>> getVideoFromDate(@Valid @RequestParam("from") String date) {
    Date from = dateHelper.StingToDate(date);
    return singerService.findAllSingersFromDate(from).switchIfEmpty(Mono.error(new EmptyDataException(ErrorCode.EMPTY_DATA_SET)))
      .collectList().map(singers -> {

        List<Song> songs = songService.findAllSongsFromDateAndSingers(from, singers)
          .switchIfEmpty(Mono.error(new EmptyDataException(ErrorCode.EMPTY_DATA_SET)))
          .collectList()
          .block();

        return videoService.findAllVideoFromDateAndSongs(from, singers, songs).collectList().map(data -> {
          final BaseResponse response = new DataListResponse<>(200, "success", data);
          return new ResponseEntity(response, HttpStatus.OK);
        }).block();
      });
  }

  // 특정 노래의 비디오 갱신 리스트
  @GetMapping("/updated/{songId}")
  public Mono<ResponseEntity<BaseResponse>> getVideoByDateAndSong(@PathVariable("songId") String songId, @Valid @RequestParam("from") String date) {
    Date from = dateHelper.StingToDate(date);
    return videoService.findAllVideoBySongIdAndFromDate(from, songId)
      .switchIfEmpty(Mono.error(new EmptyDataException(ErrorCode.EMPTY_DATA_SET)))
      .collectList()
      .map(data -> {
      final BaseResponse response = new DataListResponse<>(200, "success", data);
      return new ResponseEntity(response, HttpStatus.OK);
    });
  }

}
