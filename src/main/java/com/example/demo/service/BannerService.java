package com.example.demo.service;

import com.example.demo.model.Banner;
import reactor.core.publisher.Flux;

public interface BannerService {
  Flux<Banner> findAllAciveBanner();
  Flux<Banner> findBannerById(String bannerId);
}
