package org.example.booknotelogserver.booknotelogserver.activity.infrastructure;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface ActivityMongoRepository extends MongoRepository<ActivityEntity, String> {
}
