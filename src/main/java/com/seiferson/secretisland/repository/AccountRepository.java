package com.seiferson.secretisland.repository;

import com.seiferson.secretisland.model.Account;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AccountRepository extends MongoRepository<Account, String> {

    public Boolean existsByUsername(String username);

    public Account findByUsername(String username);
}
