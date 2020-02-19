package com.example.demo.config;

import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractReactiveMongoConfiguration;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

@Configuration
@EnableReactiveMongoRepositories(basePackages = "com.example.demo")
public class MongoConfig extends AbstractReactiveMongoConfiguration {

  @Value("${spring.data.mongodb.host}")
  private String mongoHost;

  @Value("${spring.data.mongodb.port}")
  private String mongoPort;

  @Value("${spring.data.mongodb.username}")
  private String mongoUser;

  @Value("${spring.data.mongodb.password}")
  private String mongoPassword;

  @Override
  public MongoClient reactiveMongoClient() {
    return MongoClients.create("mongodb://" + this.mongoUser + ":" + this.mongoPassword + "@" + this.mongoHost + ":" + this.mongoPort);
  }

  @Override
  protected String getDatabaseName() {
    return "nextrot";
  }

  @Bean
  public ReactiveMongoTemplate reactiveMongoTemplate() {
    return new ReactiveMongoTemplate(reactiveMongoClient(), getDatabaseName());
  }

}
