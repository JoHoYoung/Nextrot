package com.example.demo.model;


import com.example.demo.ErrorCode;
import com.example.demo.exception.InvalidParameterException;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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

  private int actionType;
  private List<Video> data;

  private Date createdAt;
  private Date updatedAt;

}
