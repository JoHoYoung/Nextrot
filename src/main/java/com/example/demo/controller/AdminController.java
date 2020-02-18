package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/v1/admin")
public class AdminController {

  @RequestMapping("")
  public String index(){

    return "index";
  }
}
