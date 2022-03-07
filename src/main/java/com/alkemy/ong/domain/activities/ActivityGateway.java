package com.alkemy.ong.domain.activities;

import java.util.List;

public interface ActivityGateway {

    Activity create(Activity activity);
    List<Activity> findAll();
    Activity findById(Long id);
     Activity update(Long id, Activity activity);

}


