package com.example.demo.controller;


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

@RestController
@RequestMapping("/song")
public class SongController {

  @Autowired
  SingerService singerService;
  @Autowired
  DateHelper dateHelper;

  // 특정 가수의 노래 리스트
  @GetMapping("")
  public Mono<ResponseEntity<BaseResponse>> getSongsBySingerId(@RequestParam("singerId")String singerId) {
    return singerService.findOneById(singerId).map(singer -> {
      return singerService.findAllSongsFromSinger(singer).collectList().map(data -> {
        final BaseResponse response = new DataListResponse<>(200, "success", data.get(0).getSongs());
        return new ResponseEntity(response, HttpStatus.OK);
      }).block();
    });
  }
//
  // 데이터의 변경이 생긴 노래목록
  @GetMapping("/updated")
  public Mono<ResponseEntity<BaseResponse>> getSongsUpdatedFrom(@RequestParam("from")String date){
    Date from = dateHelper.StingToDate(date);
    return singerService.findAllSingersFromDate(from).collectList().map(singers -> {
      return singerService.findAllSongsFromDateAndSingers(from, singers).collectList().map(data -> {
        final BaseResponse response = new DataListResponse<>(200, "success", data);
        return new ResponseEntity(response, HttpStatus.OK);
      }).block();
    });
  }

  // 데이터의 변경이 생긴 특정 가수의 노래 목록
  @GetMapping("/updated/{singerId}")
  public Mono<ResponseEntity<BaseResponse>> getSongsUpdatedSinger(@PathVariable("singerId")String id, @RequestParam("from")String date){
    Date from = dateHelper.StingToDate(date);
    return singerService.findOneById(id).map(singer -> {
      return singerService.findAllSongsFromDateAndSinger(from, singer).collectList().map(data -> {
        final BaseResponse response = new DataListResponse<>(200, "success", data.get(0).getSongs());
        return new ResponseEntity(response, HttpStatus.OK);
      }).block();
    });
  }

}
