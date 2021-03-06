package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SongAdmin {
  private String songId;
  private String lyrics;
  private String key;
  private String singerName;
  private String songName;
}
