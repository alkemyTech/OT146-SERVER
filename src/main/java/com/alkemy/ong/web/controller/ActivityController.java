package com.alkemy.ong.web.controller;
import com.alkemy.ong.domain.activities.Activity;
import com.alkemy.ong.domain.activities.ActivityService;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@RestController
@RequestMapping ("/activities")
public class ActivityController {

    private final ActivityService activityService;

    //Constr x AutoW
    public ActivityController(ActivityService activityService) {
        this.activityService = activityService;
    }

    @PostMapping (value = "")
    public ResponseEntity<ActivityDTO> save(@Valid  @RequestBody ActivityDTO dto) {

        Activity activity = activityService.save(toDomain(dto));
        return ResponseEntity.status(HttpStatus.CREATED).body(toDto(activity));
    }

    private Activity toDomain(ActivityDTO dto){
        return Activity.builder()
                .id(null)
                .name(dto.getName())
                .image(dto.getImage())
                .content(dto.getContent())
                .build();
    }

    private ActivityDTO toDto(Activity activity){
        return ActivityDTO.builder()
                .id(activity.getId())
                .name(activity.getName())
                .image(activity.getImage())
                .content(activity.getContent())
                .build();
    }

    @Builder
    @Getter
    @Setter
    @AllArgsConstructor
    private static class ActivityDTO {
        private Long id;
        @NotNull  @NotBlank

        private String name;
        private String image;

        @NotNull @NotBlank @NotEmpty
        private String content;
    }
}
