package com.seiferson.secretisland.repository;

import com.seiferson.secretisland.model.continental.Score;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ScoreRepository extends MongoRepository<Score, String> {

    Page<Score> findByGame(String game, Pageable pageable);

    Page<Score> findByPlayer(String player, Pageable pageable);

    Page<Score> findAll(Pageable pageable);
}
