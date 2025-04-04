package epam.com.task_rest_boot.dto.trainer;


public record TrainerShortDto(String username,
                              String firstName,
                              String lastName,
                              boolean isActive,
                              Integer specializationId) {
}
