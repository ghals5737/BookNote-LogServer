package org.example.booknotelogserver.booknotelogserver.activity.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.example.booknotelogserver.booknotelogserver.activity.domain.target.Target;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

// Main Activity record
@JsonDeserialize(using = ActivityDeserializer.class)
public record Activity(
        String id,
        String action,
        Actor actor,
        Target target,  // 다양한 Target 타입을 처리
        String timestamp
) {}

