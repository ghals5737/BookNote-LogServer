package org.example.booknotelogserver.booknotelogserver.activity.controller.port;

import org.example.booknotelogserver.booknotelogserver.activity.domain.Activity;

public interface ActivityService {
    void consumeActivityLog(String activityJson);
}
