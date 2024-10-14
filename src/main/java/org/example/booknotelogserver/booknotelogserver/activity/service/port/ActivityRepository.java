package org.example.booknotelogserver.booknotelogserver.activity.service.port;

import org.example.booknotelogserver.booknotelogserver.activity.infrastructure.ActivityEntity;

public interface ActivityRepository {
    void save(ActivityEntity activityEntity);
}
