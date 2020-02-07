package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Document(collection = "like")
public class Like {

  @Id
  private String id;
  private String type;
  private String UID;
  private String targetId;

  public Like(String type, String UID, String targetId){
    this.type = type;
    this.UID = UID;
    this.targetId = targetId;
  }
}
