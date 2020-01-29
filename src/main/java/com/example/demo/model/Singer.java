package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "singer")
public class Singer {

  @Id
  private String id;
  private String name;
  private List<Song> songs;
  private int like;
  private Date createdAt;
  private Date updatedAt;

  public Singer(String name, Date createdAt, Date updatedAt){
    this.name = name;
    this.like =0;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
  }
}
