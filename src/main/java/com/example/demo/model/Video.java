package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

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

}
