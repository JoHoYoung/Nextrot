package com.example.demo.controller;

import com.example.demo.ErrorCode;
import com.example.demo.exception.EmptyDataException;
import com.example.demo.response.BaseResponse;
import com.example.demo.response.DataListResponse;
import com.example.demo.service.BannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/banner")
public class BannerController {

  @Autowired
  BannerService bannerService;

  @GetMapping("/list")
  public Mono<ResponseEntity<BaseResponse>> getBannerList(){
    return bannerService.findAllAciveBanner().switchIfEmpty(Mono.error(new EmptyDataException(ErrorCode.EMPTY_DATA_SET)))
      .collectList().map(data -> {
      final BaseResponse response = new DataListResponse<>(200, "success", data);
      return new ResponseEntity<>(response, HttpStatus.OK);
    });
  }

  @GetMapping("/data")
  public Mono<ResponseEntity<BaseResponse>> doBannerAction(@RequestParam("bannerId")String bannerId) {
    return bannerService.findBannerById(bannerId).switchIfEmpty(Mono.error(new EmptyDataException(ErrorCode.EMPTY_DATA_SET)))
      .collectList().map(data -> {
      final BaseResponse response = new DataListResponse<>(200, "success", data.get(0).getData());
      return new ResponseEntity<>(response, HttpStatus.OK);
    });
  }
}
