package epam.com.task_rest_boot.dto.training;

import java.util.Date;

public record TrainingDto(String trainingName,
                          Date trainingDate,
                          Integer trainingTypeId,
                          Number trainingDuration,
                          String traineeUsername,
                          String trainerUsername) {
}
