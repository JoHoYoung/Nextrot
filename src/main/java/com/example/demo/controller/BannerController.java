package com.example.demo.controller;

import com.example.demo.BannerAction;
import com.example.demo.ErrorCode;
import com.example.demo.config.BannerActionTypeEditor;
import com.example.demo.exception.EmptyDataException;
import com.example.demo.response.BaseResponse;
import com.example.demo.response.DataListResponse;
import com.example.demo.service.BannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/banner")
public class BannerController {

  @Autowired
  BannerService bannerService;

  @InitBinder("actionType")
  public void initBinder(WebDataBinder dataBinder) {
    dataBinder.registerCustomEditor(BannerAction.class, new BannerActionTypeEditor());
  }

  @GetMapping("/list")
  public Mono<ResponseEntity<BaseResponse>> getBannerList() {
    return bannerService.findAllAciveBanner().switchIfEmpty(Mono.error(new EmptyDataException(ErrorCode.EMPTY_DATA_SET)))
      .collectList().map(data -> {
        final BaseResponse response = new DataListResponse<>(200, "success", data);
        return new ResponseEntity<>(response, HttpStatus.OK);
      });
  }

  @GetMapping("/action")
  public Mono<ResponseEntity<BaseResponse>> doBannerAction(@RequestParam("actionType")BannerAction bannerAction,
                                                           @RequestParam("key")String key) {
    return bannerAction.action(key).switchIfEmpty(Mono.error(new EmptyDataException(ErrorCode.EMPTY_DATA_SET)))
      .collectList().map(data -> {
        final BaseResponse response = new DataListResponse<>(200, "success", data);
        return new ResponseEntity<>(response, HttpStatus.OK);
      });
  }
}
