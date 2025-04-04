package epam.com.task_rest_boot.dto;

import jakarta.validation.constraints.NotBlank;

public record LoginRequestDto(
        @NotBlank(message = "username can not be null and blank")
        String username,
        @NotBlank(message = "password can not be null and blank")
        String password) {
}
