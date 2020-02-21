package com.example.demo.config;

import com.example.demo.BannerAction;

import java.beans.PropertyEditorSupport;

public class BannerActionTypeEditor extends PropertyEditorSupport {

  public void setAsText(String text) {
    try {
      setValue(BannerAction.fromValue(text));
    } catch (Exception e) {
      setValue(null);
    }
  }
}
