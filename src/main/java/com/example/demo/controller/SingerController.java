package com.example.demo.controller;

import com.example.demo.response.BaseResponse;
import com.example.demo.response.DataListResponse;
import com.example.demo.response.DataResponse;
import com.example.demo.service.SingerService;
import com.example.demo.util.DateHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.xml.ws.Response;
import java.util.Date;

@RestController
@RequestMapping("/singer")
public class SingerController {

  @Autowired
  SingerService singerService;
  @Autowired
  DateHelper dateHelper;

  @GetMapping("/getNew")
  public Mono<ResponseEntity<BaseResponse>> getNewSinger(@RequestParam("from")String date){
    Date from = dateHelper.StingToDate(date);
    return singerService.findAllNewSinger(from).collectList().map(data -> {
      final BaseResponse response = new DataListResponse<>(200,"success", data);
      return new ResponseEntity<>(response, HttpStatus.OK);
    });
  }

  @GetMapping("/getList")
  public Mono<ResponseEntity<BaseResponse>> getAllSinger(){
    return singerService.findAllSingers().collectList().map(data -> {
      final BaseResponse response = new DataListResponse<>(200, "success", data);
      return new ResponseEntity<>(response, HttpStatus.OK);
    });
  }

  @GetMapping("/getAllData")
  public Mono<ResponseEntity<BaseResponse>> getAllSingerData() {
    return singerService.findAllData().collectList().map(data -> {
      final BaseResponse baseResponse = new DataListResponse<>(200, "success", data);
      return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    });
  }

  @GetMapping("/updated")
  public Mono<ResponseEntity<BaseResponse>> getUpdateSingerData(@RequestParam("from") String date) {

    Date from = dateHelper.StingToDate(date);
    return singerService.findAllSingersFromDate(from).collectList().map(data -> {
      final BaseResponse baseResponse = new DataListResponse<>(200, "success", data);
      return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    });
  }

  @GetMapping("/get")
  public Mono<ResponseEntity<BaseResponse>> getSingerById(@RequestParam("id") String id) {
    return singerService.findOneById(id).map(data -> {
      final BaseResponse response = new DataResponse<>(200, "success", data);
      return new ResponseEntity<>(response, HttpStatus.OK);
    });
  }
}
