package com.alkemy.ong.data.gateways;

import com.alkemy.ong.data.entity.ActivityEntity;
import com.alkemy.ong.data.repository.ActivityRepository;
import com.alkemy.ong.domain.activities.Activity;
import com.alkemy.ong.domain.activities.ActivityGateway;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class DefaultActivityGateway implements ActivityGateway {

        private final ActivityRepository repository;

        public DefaultActivityGateway(ActivityRepository repository) {
            this.repository = repository;
        }


        public Activity create(Activity activity) {
            ActivityEntity entity = toEntity(activity);
            entity.setCreatedAt(LocalDateTime.now());
            return toModel(repository.save(entity));     //se retorna en modelo pero se guarda en Entity
        }


        //metodos de conversion
        private Activity toModel(ActivityEntity activityEntity){
            return Activity.builder()
                    .id(activityEntity.getId())
                    .name(activityEntity.getName())
                    .content(activityEntity.getContent())
                    .image(activityEntity.getImage())
                    .build();
        }
    //metodos de conversion
        private ActivityEntity toEntity(Activity activity){
            return ActivityEntity.builder()
                    .name(activity.getName())
                    .content(activity.getContent())
                    .image(activity.getImage())
                    .build();
        }
    }


