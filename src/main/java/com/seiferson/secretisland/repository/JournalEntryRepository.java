package com.seiferson.secretisland.repository;

import com.seiferson.secretisland.model.JournalEntry;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface JournalEntryRepository extends MongoRepository<JournalEntry, String> {

    Page<JournalEntry> findAll(Pageable pageable);

    Page<JournalEntry> findByAuthor(Pageable pageable, String author);
}
