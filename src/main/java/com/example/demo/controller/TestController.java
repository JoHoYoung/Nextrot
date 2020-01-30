package com.example.demo.controller;

import com.example.demo.response.BaseResponse;
import com.example.demo.response.DataListResponse;
import com.example.demo.model.Singer;
import com.example.demo.model.Test;
import com.example.demo.repository.SingerRespository;
import com.example.demo.repository.TestRepository;
import com.example.demo.service.SingerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/test")
public class TestController {

  @Autowired
  ReactiveMongoTemplate reactiveMongoTemplate;

  @Autowired
  TestRepository testRepository;

  @Autowired
  SingerRespository singerRespository;

  @Autowired
  SingerServiceImpl singerService;

  @RequestMapping(value = "test", method = RequestMethod.GET)
  public Mono<ResponseEntity<BaseResponse>> test() {

    Singer singer = new Singer("name",new Date(), new Date());
    singerRespository.insert(singer).subscribe();
    Mono<List<Test>> m = testRepository.findByName("hoyoung").collectList();
    return m.map(data -> {
      final BaseResponse response = new DataListResponse<>(200, "success", data);
      return new ResponseEntity<>(response, HttpStatus.OK);
    });
  }
}