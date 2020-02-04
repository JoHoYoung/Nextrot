package com.example.demo.controller;

import com.example.demo.ErrorCode;
import com.example.demo.exception.EmptyDataException;
import com.example.demo.model.Singer;
import com.example.demo.response.BaseResponse;
import com.example.demo.response.DataListResponse;
import com.example.demo.service.SingerService;
import com.example.demo.util.DateHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/video")
public class VideoController {

  @Autowired
  SingerService singerService;
  @Autowired
  DateHelper dateHelper;

  //특정 노래의 비디오 리스트
  @GetMapping("")
  public Mono<ResponseEntity<BaseResponse>> getVideoFromSong(@RequestParam("songId")String songId){
    return singerService.findAllVideoBySongId(songId).map(data -> data.getSongs()).collectList()
      .map(k -> {
        final BaseResponse response = new DataListResponse<>(200,"success",k.get(0).get(0).getVideo());
        return new ResponseEntity<>(response, HttpStatus.OK);
      });
  }

  // 갱신이 생긴 모든 비디오 리스트
  @GetMapping("/updated")
  public Mono<ResponseEntity<BaseResponse>> getVideoFromDate(@RequestParam("from")String date){
    Date from = dateHelper.StingToDate(date);
    return singerService.findAllSingersFromDate(from).collectList().map(singers -> {
      List<Singer> songs = singerService.findAllSongsFromDateAndSingers(from, singers).collectList().block();
      return singerService.findAllVideoFromDateAndSongs(from, songs).collectList().map(data -> {
        final BaseResponse response = new DataListResponse<>(200, "success", data);
        return new ResponseEntity(response, HttpStatus.OK);
      }).block();
    });
  }

  // IndexOutOfBoundsException
  // 특정 노래의 갱신 리스트
  @GetMapping("/updated/{songId}")
  public Mono<ResponseEntity<BaseResponse>> getVideoByDateAndSong(@PathVariable("songId")String songId, @RequestParam("from")String date){
    Date from = dateHelper.StingToDate(date);
    return singerService.findAllVideoBySongIdAndFromDate(from, songId).collectList().map(data -> {
      try{
        final BaseResponse response = new DataListResponse<>(200, "success", data.get(0).getSongs().get(0).getVideo());
        return new ResponseEntity(response, HttpStatus.OK);
      }catch(IndexOutOfBoundsException e){
        throw new EmptyDataException(ErrorCode.EMPTY_DATA_SET);
      }
    });
  }

}
