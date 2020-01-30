package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Video {
  private String id;
  private String songId;
  private String key;
  private int like;
  private int view;
  private Date createdAt;
  private Date updatedAt;

  public Video(String key){
    this.id = UUID.randomUUID().toString();
    this.like = 0;
    this.view = 0;
    this.createdAt = new Date();
    this.updatedAt = new Date();
  }
}
