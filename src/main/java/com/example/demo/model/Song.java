package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Song {

  @Id
  private String id;
  private String singerId;
  private String name;
  private String lyrics;
  private int like;
  private int view;
  private List<Video> video;
  private Date createdAt;
  private Date updatedAt;

  public Song(String name, String lyrics){
    this.id = UUID.randomUUID().toString();
    this.name = name;
    this.lyrics = lyrics;
    this.createdAt = new Date();
    this.updatedAt = new Date();
    this.video = new ArrayList();
  }

}
