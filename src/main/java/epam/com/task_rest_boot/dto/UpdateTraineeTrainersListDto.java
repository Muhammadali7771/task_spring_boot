package epam.com.task_rest_boot.dto;

import java.util.List;

public record UpdateTraineeTrainersListDto(String traineeUsername,
                                              List<String> trainers) {
}
