package org.example.booknotelogserver.booknotelogserver.activity.infrastructure;

import lombok.RequiredArgsConstructor;
import org.example.booknotelogserver.booknotelogserver.activity.domain.Activity;
import org.example.booknotelogserver.booknotelogserver.activity.domain.ActivitySearch;
import org.example.booknotelogserver.booknotelogserver.activity.service.port.ActivityRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ActivityRepositoryImpl implements ActivityRepository {
    private final ActivityMongoRepository activityMongoRepository;
    private final MongoTemplate mongoTemplate;

    @Override
    public void save(ActivityEntity activityEntity) {
        activityMongoRepository.save(activityEntity);
    }

    @Override
    public Page<Activity> findByActor_Id(long actorId, Pageable pageable) {
        return activityMongoRepository.findByActor_Id(actorId, pageable).map(ActivityEntity::toModel);
    }

    @Override
    public Page<Activity> findByCriteria(ActivitySearch activitySearch) {
        Query query = new Query();
        PageRequest pageable = PageRequest.of(activitySearch.page(), activitySearch.size());

        // actorId 필터 추가
        query.addCriteria(Criteria.where("actor.id").is(activitySearch.actorId()));

        // timestamp 범위 필터 추가
        query.addCriteria(Criteria.where("timestamp").gte(activitySearch.from()).lte(activitySearch.to()));

        // searchActions가 'all'이 아니면 action 필터 추가
        if (!activitySearch.searchActions().contains("all")) {
            query.addCriteria(Criteria.where("action").in(activitySearch.searchActions()));
        }

        // 페이징 처리
        long total = mongoTemplate.count(query, ActivityEntity.class);
        query.with(pageable);

        System.out.println(query);

        List<Activity> activities = mongoTemplate.find(query, ActivityEntity.class).stream().map(ActivityEntity::toModel).toList();

        return new PageImpl<>(activities, pageable, total);
    }
}
