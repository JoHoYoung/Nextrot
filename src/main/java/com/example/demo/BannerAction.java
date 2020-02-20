package com.example.demo;

import com.example.demo.exception.InvalidParameterException;
import com.example.demo.service.BannerService;
import com.example.demo.service.SingerService;
import com.example.demo.service.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import java.util.function.Function;

public enum BannerAction {

  GET_OWN_SONG_DATA(0, "Fetch Banners Own Data"),
  GET_SINGERS_DATA(1, "Fetch Singers Data"),
  INVALID_ENUM_TYPE(-1, "Invalid Enum Casting");

  private int typeNum;
  private String description;

  BannerAction(int typeNum, String description){
    this.typeNum = typeNum;
    this.description = description;
  }

  @Component
  private static class ActionBeanInjector {
    @Autowired
    private SongService songService;
    @Autowired
    SingerService singerService;
    @Autowired
    BannerService bannerService;

    @PostConstruct
    public void postConstruct() {
      BannerAction.INVALID_ENUM_TYPE.setExpression(key -> Flux.error(new InvalidParameterException(ErrorCode.INVALID_BANNER_ACTION)));
      BannerAction.GET_OWN_SONG_DATA.setExpression(key -> bannerService.findBannerOwnData(key));
      BannerAction.GET_SINGERS_DATA.setExpression(key -> songService.findAllSongsFromSingerId(key));
    }
  }

  public static BannerAction fromValue(String param) {

    int value = Integer.parseInt(param);
    for (BannerAction bannerAction: BannerAction.values()) {
      if (bannerAction.typeNum == value) {
        return bannerAction;
      }
    }
    return BannerAction.INVALID_ENUM_TYPE;
  }

  private Function<String, Flux> expression;

  public void setExpression(Function<String, Flux> expression) {
    this.expression = expression;
  }

  public <T> Flux<T> action(String key) {
    return expression.apply(key);
  }

}
