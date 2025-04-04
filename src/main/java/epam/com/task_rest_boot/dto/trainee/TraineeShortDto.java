package epam.com.task_rest_boot.dto.trainee;


public record TraineeShortDto(String username,
                              String firstName,
                              String lastName,
                              boolean isActive
) {
}
