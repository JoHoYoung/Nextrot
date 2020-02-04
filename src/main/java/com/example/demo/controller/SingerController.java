package com.example.demo.controller;

import com.example.demo.ErrorCode;
import com.example.demo.exception.EmptyDataException;
import com.example.demo.response.BaseResponse;
import com.example.demo.response.DataListResponse;
import com.example.demo.response.DataResponse;
import com.example.demo.service.SingerService;
import com.example.demo.util.DateHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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

  @GetMapping("/new")
  public Mono<ResponseEntity<BaseResponse>> getNewSinger(@RequestParam("from")String date){
    Date from = dateHelper.StingToDate(date);
    return singerService.findAllNewSinger(from).collectList().map(data -> {

      if(data.isEmpty()){
        throw new EmptyDataException(ErrorCode.EMPTY_DATA_SET);
      }

      final BaseResponse response = new DataListResponse<>(200,"success", data);
      return new ResponseEntity<>(response, HttpStatus.OK);

    });
  }

  @GetMapping("/list")
  public Mono<ResponseEntity<BaseResponse>> getAllSinger(){
    return singerService.findAllSingers().collectList().map(data -> {

      if(data.isEmpty()){
        throw new EmptyDataException(ErrorCode.EMPTY_DATA_SET);
      }

      final BaseResponse response = new DataListResponse<>(200, "success", data);
      return new ResponseEntity<>(response, HttpStatus.OK);
    });
  }

  @GetMapping("/allData")
  public Mono<ResponseEntity<BaseResponse>> getAllSingerData() {
    return singerService.findAllData().collectList().map(data -> {

      if(data.isEmpty()){
        throw new EmptyDataException(ErrorCode.EMPTY_DATA_SET);
      }

      final BaseResponse baseResponse = new DataListResponse<>(200, "success", data);
      return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    });
  }

  @GetMapping("/updated")
  public Mono<ResponseEntity<BaseResponse>> getUpdateSingerData(@RequestParam("from") String date) {

    Date from = dateHelper.StingToDate(date);
    return singerService.findAllSingersFromDate(from).collectList().map(data -> {

      if(data.isEmpty()){
        throw new EmptyDataException(ErrorCode.EMPTY_DATA_SET);
      }

      final BaseResponse baseResponse = new DataListResponse<>(200, "success", data);
      return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    });
  }

  @GetMapping("/{singerId}")
  public Mono<ResponseEntity<BaseResponse>> getSingerById(@PathVariable("singerId") String singerId) {
    return singerService.findOneById(singerId).switchIfEmpty().map(data -> {

      final BaseResponse response = new DataResponse<>(200, "success", data);
      return new ResponseEntity<>(response, HttpStatus.OK);
    });
  }
}
