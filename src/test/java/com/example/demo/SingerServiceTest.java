package com.example.demo;

import com.example.demo.model.Singer;
import com.example.demo.model.Song;
import com.example.demo.model.Video;
import com.example.demo.service.SingerService;
import com.example.demo.util.DateHelper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.util.DateUtil;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Flux;

import java.util.Date;

import static org.hamcrest.CoreMatchers.is;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@AutoConfigureMockMvc
public class SingerServiceTest {

  @Autowired
  SingerService singerService;
  @Autowired
  DateHelper dateHelper;
  @Autowired
  ObjectMapper objectMapper;

//  @Test
//  public void AinsertSingerTest() {
//    Singer singer = new Singer("HoYoung");
//    singerService.createSinger(singer).subscribe();
//
//    Flux<Singer> singerFlux = singerService.findSingersByName("HoYoung");
//    Mono<List<Singer>> data = singerFlux.collectList();
//    data.subscribe(singers -> {
//      System.out.println(singers);
//      for (Singer singer1 : singers) {
//        Assert.assertThat(singer1.getId(), is("HoYoung"));
//        singerService.deleteSingerById(singer1.getId()).subscribe();
//      }
//    });
//  }

//  @Test
//  public void BinsertSongToSingerTest(){
//    Singer singer = new Singer("HoYoung");
//    Song song = new Song("testName", "testLyrics");
//
//    singerService.createSinger(singer).subscribe(data -> {
//      singerService.insertSongToSingerById(data.getId(), song).subscribe(a -> {
//        System.out.println(a);
//      });
//
//    });
//  }



}
