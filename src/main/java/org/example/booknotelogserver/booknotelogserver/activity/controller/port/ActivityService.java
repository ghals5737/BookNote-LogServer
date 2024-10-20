package org.example.booknotelogserver.booknotelogserver.activity.controller.port;

import org.example.booknotelogserver.booknotelogserver.activity.controller.response.ActivityPageResponse;
import org.example.booknotelogserver.booknotelogserver.activity.domain.Activity;
import org.example.booknotelogserver.booknotelogserver.activity.domain.ActivitySearch;
import org.example.booknotelogserver.booknotelogserver.activity.infrastructure.ActivityEntity;
import org.springframework.data.domain.Page;

public interface ActivityService {
    void consumeActivityLog(String activityJson);
    ActivityPageResponse getActivitiesByActorId(long actorId, int page, int size);
    ActivityPageResponse searchActivities(ActivitySearch activitySearch);
}
