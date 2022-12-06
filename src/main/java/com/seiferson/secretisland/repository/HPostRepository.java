package com.seiferson.secretisland.repository;

import com.seiferson.secretisland.model.HPost;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface HPostRepository extends ReactiveMongoRepository<HPost, String> {

}
