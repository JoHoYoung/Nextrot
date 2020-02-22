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
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.util.Date;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/singers")
public class SingerController {

  @Autowired
  SingerService singerService;
  @Autowired
  DateHelper dateHelper;

  @GetMapping("/new")
  public Mono<ResponseEntity<BaseResponse>> getNewSinger(@Valid @RequestParam("from") String date,
                                                         @RequestParam(required = false, value = "page", defaultValue = "0") int page,
                                                         @RequestParam(required = false, value = "pageSize", defaultValue = "10") int pageSize) {
    Date from = dateHelper.StingToDate(date);
    return singerService.findAllNewSinger(from).switchIfEmpty(Mono.error(new EmptyDataException(ErrorCode.EMPTY_DATA_SET)))
      .collectList().map(data -> {
        final BaseResponse response = new DataListResponse<>(200, "success",
          data.stream().skip(page * pageSize).limit(pageSize).collect(Collectors.toList()));
        return new ResponseEntity<>(response, HttpStatus.OK);
      });
  }

  @GetMapping("/list")
  public Mono<ResponseEntity<BaseResponse>> getAllSinger(@RequestParam(required = false, value = "page", defaultValue = "0") int page,
                                                         @RequestParam(required = false, value = "pageSize", defaultValue = "10") int pageSize) {
    return singerService.findAllSingers().switchIfEmpty(Mono.error(new EmptyDataException(ErrorCode.EMPTY_DATA_SET)))
      .collectList()
      .map(data -> {
        final BaseResponse response = new DataListResponse<>(200, "success",
          data.stream().skip(page * pageSize).limit(pageSize).collect(Collectors.toList()));
        return new ResponseEntity<>(response, HttpStatus.OK);
      });
  }

  @GetMapping("/metadata")
  public Mono<ResponseEntity<BaseResponse>> getAllSingerData(@RequestParam(required = false, value = "page", defaultValue = "0") int page,
                                                             @RequestParam(required = false, value = "pageSize", defaultValue = "10") int pageSize) {
    return singerService.findAllData().switchIfEmpty(Mono.error(new EmptyDataException(ErrorCode.EMPTY_DATA_SET)))
      .collectList()
      .map(data -> {
        final BaseResponse baseResponse = new DataListResponse<>(200, "success",
          data.stream().skip(page * pageSize).limit(pageSize).collect(Collectors.toList()));
        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
      });
  }

  @GetMapping("/updated")
  public Mono<ResponseEntity<BaseResponse>> getUpdateSingerData(@Valid @RequestParam("from") String date,
                                                                @RequestParam(required = false, value = "page", defaultValue = "0") int page,
                                                                @RequestParam(required = false, value = "pageSize", defaultValue = "10") int pageSize) {
    Date from = dateHelper.StingToDate(date);
    return singerService.findAllSingersFromDate(from).switchIfEmpty(Mono.error(new EmptyDataException(ErrorCode.EMPTY_DATA_SET)))
      .collectList()
      .map(data -> {
        final BaseResponse baseResponse = new DataListResponse<>(200, "success",
          data.stream().skip(page * pageSize).limit(pageSize).collect(Collectors.toList()));
        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
      });
  }

  @GetMapping("/{singerId}")
  public Mono<ResponseEntity<BaseResponse>> getSingerById(@PathVariable("singerId") String singerId) {
    return singerService.findOneById(singerId).switchIfEmpty(Mono.error(new EmptyDataException(ErrorCode.INVALID_KEY))).map(data -> {
      final BaseResponse response = new DataResponse<>(200, "success", data);
      return new ResponseEntity<>(response, HttpStatus.OK);
    });
  }
}
