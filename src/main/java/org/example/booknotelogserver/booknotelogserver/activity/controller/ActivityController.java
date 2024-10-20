package org.example.booknotelogserver.booknotelogserver.activity.controller;

import lombok.RequiredArgsConstructor;
import org.example.booknotelogserver.booknotelogserver.activity.controller.port.ActivityService;
import org.example.booknotelogserver.booknotelogserver.activity.controller.response.ActivityPageResponse;
import org.example.booknotelogserver.booknotelogserver.activity.domain.Activity;
import org.example.booknotelogserver.booknotelogserver.activity.domain.ActivitySearch;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/activities")
@RequiredArgsConstructor
public class ActivityController {
    private final ActivityService activityService;

    @GetMapping
    public ResponseEntity<ActivityPageResponse> getActivities(
            @RequestParam long actorId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        return ResponseEntity.ok(activityService.getActivitiesByActorId(actorId,page,size));
    }

    @PostMapping("/search")
    public ResponseEntity<ActivityPageResponse> searchActivities(@RequestBody ActivitySearch activitySearch) {
        return ResponseEntity.ok(activityService.searchActivities(activitySearch));
    }
}
