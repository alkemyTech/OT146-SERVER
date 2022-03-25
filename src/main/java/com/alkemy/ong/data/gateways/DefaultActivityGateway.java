package com.alkemy.ong.data.gateways;

import com.alkemy.ong.data.entities.ActivityEntity;
import com.alkemy.ong.data.repositories.ActivityRepository;
import com.alkemy.ong.domain.activities.Activity;
import com.alkemy.ong.domain.activities.ActivityGateway;
import com.alkemy.ong.web.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

import static java.util.stream.Collectors.toList;

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

    @Override
    public List<Activity> findAll() {
        List<ActivityEntity> actEntity = repository.findAll();
        return toModelList(actEntity);
    }


    @Override
    public Activity findById(Long id) {
        ActivityEntity  actEntity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("There is no Activity with such ID"));
        return toModel(actEntity);
    }

    @Override
    public Activity update(Long id, Activity activity) {
        ActivityEntity  actEntity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("There is no Activity with such ID"));
        actEntity.setName(activity.getName());
        actEntity.setImage(activity.getImage());
        actEntity.setContent(activity.getContent());
        actEntity.setUpdatedAt(LocalDateTime.now());
        repository.save(actEntity);
        return toModel(actEntity);
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

    private List<Activity> toModelList(List<ActivityEntity> activities)
        {
            return activities.stream().map(this::toModel).collect(toList());
        }
    }


