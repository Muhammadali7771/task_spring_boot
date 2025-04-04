package epam.com.task_rest_boot.dto.trainer;


import com.fasterxml.jackson.annotation.JsonProperty;
import epam.com.task_rest_boot.dto.trainee.TraineeShortDto;

import java.util.List;

public record TrainerDto(String firstName,
                         String lastName,
                         boolean isActive,
                         Integer specializationId,
                         @JsonProperty("trainee_list")
                         List<TraineeShortDto> traineeShortDtos) {
}
