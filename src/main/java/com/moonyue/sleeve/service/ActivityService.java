package com.moonyue.sleeve.service;

import com.moonyue.sleeve.model.Activity;
import com.moonyue.sleeve.repository.ActivityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ActivityService {

    @Autowired
    private ActivityRepository activityRepository;

    public Optional<Activity> getByName(String name){
        return activityRepository.findByName(name);
    }
}
