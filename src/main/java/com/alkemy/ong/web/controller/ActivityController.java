package com.alkemy.ong.web.controller;

import com.alkemy.ong.domain.activities.Activity;
import com.alkemy.ong.domain.activities.ActivityService;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/activities")
public class ActivityController {

    private final ActivityService activityService;


    public ActivityController(ActivityService activityService) {
        this.activityService = activityService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(value = "")
    public ResponseEntity<ActivityDTO> save(@Valid @RequestBody ActivityDTO dto) {

        Activity activity = activityService.save(toDomain(dto));
        return ResponseEntity.status(HttpStatus.CREATED).body(toDto(activity));
    }

        @GetMapping
        public ResponseEntity<List<ActivityDTO>> getActivities(){
            List<Activity> activitiesList = activityService.getActivities();
            List<ActivityDTO> dtoList = toDtoList(activitiesList);
            return ResponseEntity.ok().body(dtoList);
        }

    @PreAuthorize("hasRole('USER') OR hasRole('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<ActivityDTO> showId(@PathVariable Long id){
        Activity activities  = activityService.findById(id);
        ActivityController.ActivityDTO activitiesDTO = toDto(activities);
        return ResponseEntity.ok().body(activitiesDTO);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<ActivityDTO> updateActivity(@PathVariable Long id , @Valid @RequestBody ActivityDTO dto){

    Activity activity = activityService.findById(id);
    activity.setName(dto.getName());
    activity.setImage(dto.getImage());
    activity.setContent(dto.getContent());
    activity = activityService.update(id,activity);
    return  ResponseEntity.ok().body(toDto(activity));

    }


    private Activity toDomain(ActivityDTO dto) {
        return Activity.builder()
                .id(dto.getId())
                .name(dto.getName())
                .image(dto.getImage())
                .content(dto.getContent())
                .build();
    }

    private ActivityDTO toDto(Activity activity) {
        return ActivityDTO.builder()
                .id(activity.getId())
                .name(activity.getName())
                .image(activity.getImage())
                .content(activity.getContent())
                .build();
    }

    private List<ActivityDTO> toDtoList(List<Activity> activities) {
        return activities.stream().map(this::toDto).collect(Collectors.toList());
    }

    @Builder
    @Getter
    @Setter
    @AllArgsConstructor
    private static class ActivityDTO {
        private Long id;
        @NotNull
        @NotBlank

        private String name;
        private String image;

        @NotNull
        @NotBlank
        @NotEmpty
        private String content;
    }
}
