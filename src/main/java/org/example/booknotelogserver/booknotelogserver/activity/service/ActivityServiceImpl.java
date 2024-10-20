package org.example.booknotelogserver.booknotelogserver.activity.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.example.booknotelogserver.booknotelogserver.activity.controller.port.ActivityService;
import org.example.booknotelogserver.booknotelogserver.activity.controller.response.ActivityPageResponse;
import org.example.booknotelogserver.booknotelogserver.activity.domain.Activity;
import org.example.booknotelogserver.booknotelogserver.activity.domain.ActivitySearch;
import org.example.booknotelogserver.booknotelogserver.activity.infrastructure.ActivityEntity;
import org.example.booknotelogserver.booknotelogserver.activity.service.port.ActivityRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Builder
@RequiredArgsConstructor
public class ActivityServiceImpl implements ActivityService {
    private final ActivityRepository activityRepository;
    private final ObjectMapper objectMapper;
    private final String topic="activity-log-topic";


    @Override
    @KafkaListener(topics = topic, groupId = "${spring.kafka.consumer.group-id}")
    public void consumeActivityLog(String activityJson) {
        try {
            Activity activity = objectMapper.readValue(activityJson, Activity.class);
            activityRepository.save(ActivityEntity.from(activity));
        } catch (Exception e) {
            e.printStackTrace(); // 오류 처리
        }
    }

    @Override
    public ActivityPageResponse getActivitiesByActorId(long actorId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Activity> activityEntities = activityRepository.findByActor_Id(actorId, pageable);
        boolean hasNext = activityEntities.hasNext();

        return new ActivityPageResponse(
                activityEntities.getContent().stream().toList(),
                activityEntities.getTotalPages(),
                page,
                hasNext
        );
    }

    @Override
    public ActivityPageResponse searchActivities(ActivitySearch activitySearch) {
        Page<Activity> activityEntities = activityRepository.findByCriteria(activitySearch);
        boolean hasNext = activityEntities.hasNext();
        if(activityEntities.getContent().isEmpty()){
            return new ActivityPageResponse(
                    new ArrayList<>(),
                    0,
                    0,
                    false
            );
        }
        return new ActivityPageResponse(
                activityEntities.getContent().stream().toList(),
                activityEntities.getTotalPages(),
                activitySearch.page(),
                hasNext
        );
    }
}
