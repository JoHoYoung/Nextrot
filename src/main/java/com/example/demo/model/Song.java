package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.util.Date;
import java.util.List;

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
}
