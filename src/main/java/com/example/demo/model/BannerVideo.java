package com.example.demo.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class BannerVideo {

  @Id
  private String id;
  private String singerName;
  private String songName;
  private String key;
  private Date createdAt;
  private Date updatedAt;
}
