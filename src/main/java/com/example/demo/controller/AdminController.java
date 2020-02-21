package com.example.demo.controller;

import com.example.demo.model.SongAdmin;
import com.example.demo.response.BaseResponse;
import com.example.demo.response.DataListResponse;
import com.example.demo.service.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/admin")
public class AdminController {

  @Autowired
  SongService songService;

  @GetMapping("/data")
  public Mono<ResponseEntity<BaseResponse>> index(){
    return songService.getnullData().collectList().map((data -> {
      final BaseResponse response = new DataListResponse<>(200, "success", data);
      return new ResponseEntity<>(response, HttpStatus.OK);
    }));
  }

  @PostMapping("/update")
  public Mono<ResponseEntity<BaseResponse>> update(@RequestBody SongAdmin songAdmin){
    return songService.updateField(songAdmin.getSongId(), songAdmin.getLyrics(), songAdmin.getKey()).map(el ->
     new ResponseEntity<>(new BaseResponse(200, "success"), HttpStatus.OK)
    );
  }

}
