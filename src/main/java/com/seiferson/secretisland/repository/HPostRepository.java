package com.seiferson.secretisland.repository;

import com.seiferson.secretisland.model.HPost;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface HPostRepository extends MongoRepository<HPost, String> {
    Page<HPost> findAll(Pageable pageable);

}
