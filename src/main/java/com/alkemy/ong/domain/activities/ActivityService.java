package com.alkemy.ong.domain.activities;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
    public class ActivityService {

        private final ActivityGateway activityGateway;

        public ActivityService(ActivityGateway activityGateway) {
            this.activityGateway = activityGateway;
        }

        public  Activity save(Activity activity){

            return activityGateway.create(activity);
        }

        public Activity update(Long id, Activity activity){

            return  activityGateway.update(id,activity);

        }

        public List<Activity> getActivities() {
            return activityGateway.findAll();
        }

        public Activity findById(Long id) {
            return activityGateway.findById(id);
    }
}


