package com.example.demo.controller;


import com.example.demo.response.BaseResponse;
import com.example.demo.response.DataListResponse;
import com.example.demo.service.SingerService;
import com.example.demo.service.SongService;
import com.example.demo.util.DateHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.util.Date;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/songs")
public class SongController {

  @Autowired
  SingerService singerService;
  @Autowired
  SongService songService;
  @Autowired
  DateHelper dateHelper;

  // 특정 가수의 노래 리스트
  @GetMapping("")
  public Mono<ResponseEntity<BaseResponse>> getSongsBySingerId(@Valid @RequestParam("singerId")String singerId,
                                                               @RequestParam(required = false, value = "page", defaultValue = "0" )int page,
                                                               @RequestParam(required = false, value = "pageSize", defaultValue = "10")int pageSize) {
    return singerService.findOneById(singerId).map(singer ->
      songService.findAllSongsFromSinger(singer).collectList().map(data -> {
        final BaseResponse response = new DataListResponse<>(200, "success",
          data.stream().skip(page*pageSize).limit(pageSize).collect(Collectors.toList()));
        return new ResponseEntity<>(response, HttpStatus.OK);
      }).block());
  }
  // 데이터의 변경이 생긴 노래목록
  @GetMapping("/updated")
  public Mono<ResponseEntity<BaseResponse>> getSongsUpdatedFrom(@Valid @RequestParam("from")String date,
                                                                @RequestParam(required = false, value = "page", defaultValue = "0" )int page,
                                                                @RequestParam(required = false, value = "pageSize", defaultValue = "10")int pageSize){
    Date from = dateHelper.StingToDate(date);
    return singerService.findAllSingersFromDate(from).collectList().map(singers -> {
      return songService.findAllSongsFromDateAndSingers(from, singers).collectList().map(data -> {
        final BaseResponse response = new DataListResponse<>(200, "success",
          data.stream().skip(page*pageSize).limit(pageSize).collect(Collectors.toList()));
        return new ResponseEntity(response, HttpStatus.OK);
      }).block();
    });
  }

  // 데이터의 변경이 생긴 특정 가수의 노래 목록
  @GetMapping("/updated/{singerId}")
  public Mono<ResponseEntity<BaseResponse>> getSongsUpdatedSinger(@PathVariable("singerId")String id, @Valid @RequestParam("from")String date,
                                                                  @RequestParam(required = false, value = "page", defaultValue = "0" )int page,
                                                                  @RequestParam(required = false, value = "pageSize", defaultValue = "10")int pageSize){
    Date from = dateHelper.StingToDate(date);
    return singerService.findOneById(id).map(singer -> {
      return songService.findAllSongsFromDateAndSinger(from, singer).collectList().map(data -> {
        final BaseResponse response = new DataListResponse<>(200, "success",
          data.stream().skip(page*pageSize).limit(pageSize).collect(Collectors.toList()));
        return new ResponseEntity(response, HttpStatus.OK);
      }).block();
    });
  }

}
