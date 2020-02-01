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

  @Test
  public void CinsertVideoToSongTest() throws Exception{
    //  singerService.createSinger(new Singer("SangHo")).subscribe();
    //singerService.insertSongToSingerById("5e32c8cc50823b60f80758e5", new Song("Music2", "Musice2Lyrics")).subscribe();
   // singerService.insertSongToSingerById("5e32c8cc50823b60f80758e5", new Song("Music2", "Musice2Lyrics")).subscribe();
    //    singerService.insertVideoToSong("5e328f15b7e4937f52c67679", "36483ebb-ec5b-442f-9487-1c785370b56d", new Video("FromYouTube2"))
//      .subscribe();
//    singerService.insertVideoToSong("5e328f15b7e4937f52c67679", "36483ebb-ec5b-442f-9487-1c785370b56d", new Video("FromKaKao"))
//      .subscribe();
    Date date = dateHelper.StingToDate("2018-01-01-01:00:00");

    singerService.findAllData().collectList().subscribe(data -> {
      try{
      System.out.println(objectMapper.writeValueAsString(data));
    }catch(Exception e){

    }}
      );
    singerService.findAllSingersFromDate(date)
      .collectList().subscribe(singers -> {
      //System.out.println(singers);
      singerService.findAllSongsFromDateAndSingers(date, singers).collectList()
        .subscribe(songs -> {
            try{
  //            System.out.println(objectMapper.writeValueAsString(songs));
//
          }catch (Exception e){

          }
            singerService.findAllVideoFromDateAndSongs(date,songs).collectList()
          .subscribe(videos -> {
            try{
            //  System.out.println(objectMapper.writeValueAsString(videos));
            }catch (Exception e){

            }
          });
        });
    });

  }

}
