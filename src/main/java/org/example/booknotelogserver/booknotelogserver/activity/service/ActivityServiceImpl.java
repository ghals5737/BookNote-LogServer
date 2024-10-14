package org.example.booknotelogserver.booknotelogserver.activity.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.example.booknotelogserver.booknotelogserver.activity.controller.port.ActivityService;
import org.example.booknotelogserver.booknotelogserver.activity.domain.Activity;
import org.example.booknotelogserver.booknotelogserver.activity.infrastructure.ActivityEntity;
import org.example.booknotelogserver.booknotelogserver.activity.service.port.ActivityRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

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
}
