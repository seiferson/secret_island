package com.seiferson.secretisland.repository;

import com.seiferson.secretisland.model.HPost;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface HPostRepository extends MongoRepository<HPost, String> {

}
