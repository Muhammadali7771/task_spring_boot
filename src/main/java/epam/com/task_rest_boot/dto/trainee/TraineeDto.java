package epam.com.task_rest_boot.dto.trainee;


import com.fasterxml.jackson.annotation.JsonProperty;
import epam.com.task_rest_boot.dto.trainer.TrainerShortDto;

import java.util.Date;
import java.util.List;

public record TraineeDto(String firstName,
                         String lastName,
                         boolean isActive,
                         Date dateOfBirth,
                         String address,
                         @JsonProperty("trainers")
                         List<TrainerShortDto> trainerDtos
) {
}
