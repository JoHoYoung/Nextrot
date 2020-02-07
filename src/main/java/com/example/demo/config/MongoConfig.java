package com.example.demo.config;

import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.ReactiveMongoDatabaseFactory;
import org.springframework.data.mongodb.config.AbstractReactiveMongoConfiguration;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.SimpleReactiveMongoDatabaseFactory;
import org.springframework.data.mongodb.core.convert.MongoConverter;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

@Configuration
@EnableReactiveMongoRepositories(basePackages = "com.example.demo")
public class MongoConfig {

  @Bean
  public SimpleReactiveMongoDatabaseFactory ReactiveMongoDatabaseFactory(MongoClient mongoClient) {
    return new SimpleReactiveMongoDatabaseFactory(mongoClient, "singer");
  }

  @Bean(name = "reactiveMongoTemplate")
  public ReactiveMongoTemplate ReactiveMongoTemplate(ReactiveMongoDatabaseFactory reactiveMongoDatabaseFactory,
                                                         MongoConverter converter) {
    return new ReactiveMongoTemplate(reactiveMongoDatabaseFactory, converter);
  }

}
