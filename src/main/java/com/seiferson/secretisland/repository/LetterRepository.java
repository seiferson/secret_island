package com.seiferson.secretisland.repository;

import com.seiferson.secretisland.model.Letter;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface LetterRepository extends MongoRepository<Letter, String> {

}
