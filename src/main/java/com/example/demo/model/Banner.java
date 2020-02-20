package com.example.demo.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Banner {

  @Id
  private String id;
  private String imageUrl;
  private String state;
  private String title;
  private String key;
  private int actionType;
  private List<Song> data;

  private Date createdAt;
  private Date updatedAt;

}
