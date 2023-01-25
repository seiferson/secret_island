package com.seiferson.secretisland.repository;

import com.seiferson.secretisland.model.Notification;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface NotificationRepository extends MongoRepository<Notification, String> {
}
