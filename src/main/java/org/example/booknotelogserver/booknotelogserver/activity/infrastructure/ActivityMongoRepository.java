package org.example.booknotelogserver.booknotelogserver.activity.infrastructure;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ActivityMongoRepository extends MongoRepository<ActivityEntity, String> {
    Page<ActivityEntity> findByActor_Id(long actorId, Pageable pageable);
}
