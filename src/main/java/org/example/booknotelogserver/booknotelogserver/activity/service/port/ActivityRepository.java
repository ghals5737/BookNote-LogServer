package org.example.booknotelogserver.booknotelogserver.activity.service.port;

import org.example.booknotelogserver.booknotelogserver.activity.domain.Activity;
import org.example.booknotelogserver.booknotelogserver.activity.domain.ActivitySearch;
import org.example.booknotelogserver.booknotelogserver.activity.infrastructure.ActivityEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ActivityRepository {
    void save(ActivityEntity activityEntity);

    Page<Activity> findByActor_Id(long actorId, Pageable pageable);
    Page<Activity> findByCriteria(ActivitySearch activitySearch);
}
