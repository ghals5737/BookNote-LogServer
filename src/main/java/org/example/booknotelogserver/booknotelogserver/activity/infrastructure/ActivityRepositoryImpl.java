package org.example.booknotelogserver.booknotelogserver.activity.infrastructure;

import lombok.RequiredArgsConstructor;
import org.example.booknotelogserver.booknotelogserver.activity.service.port.ActivityRepository;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ActivityRepositoryImpl implements ActivityRepository {
    private final ActivityMongoRepository activityMongoRepository;

    @Override
    public void save(ActivityEntity activityEntity) {
        activityMongoRepository.save(activityEntity);
    }
}
