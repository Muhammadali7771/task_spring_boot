package epam.com.task_rest_boot.dto.trainer;


public record TrainerUpdateDto(String firstName,
                               String lastName,
                               boolean isActive,
                               Integer specializationId) {
}
