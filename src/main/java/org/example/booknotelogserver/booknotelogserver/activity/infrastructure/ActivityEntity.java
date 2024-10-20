package org.example.booknotelogserver.booknotelogserver.activity.infrastructure;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.booknotelogserver.booknotelogserver.activity.domain.Activity;
import org.example.booknotelogserver.booknotelogserver.activity.domain.Actor;
import org.example.booknotelogserver.booknotelogserver.activity.domain.target.Target;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

@Document(collection = "activity_logs")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ActivityEntity {
    @Id
    private String id;
    private String action;
    private Actor actor;
    private Target target;
    private long timestamp;

    public Activity toModel() {
        return new Activity(
                this.id,
                this.action,
                this.actor,
                this.target,
                this.timestamp
        );
    }

    public static ActivityEntity from(Activity activity){
        ActivityEntity activityEntity=new ActivityEntity();
        activityEntity.action=activity.action();
        activityEntity.actor=activity.actor();
        activityEntity.target=activity.target();
        activityEntity.timestamp=activity.timestamp();

        return activityEntity;
    }
}

