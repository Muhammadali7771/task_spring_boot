package epam.com.task_rest_boot.service;

import epam.com.task_rest_boot.dto.ChangeLoginDto;
import epam.com.task_rest_boot.dto.LoginRequestDto;
import epam.com.task_rest_boot.dto.RegistrationResponseDto;
import epam.com.task_rest_boot.dto.trainer.TrainerCreateDto;
import epam.com.task_rest_boot.dto.trainer.TrainerDto;
import epam.com.task_rest_boot.dto.trainer.TrainerUpdateDto;
import epam.com.task_rest_boot.dto.training.TrainingDto;

import java.util.Date;
import java.util.List;

public interface TrainerService {
    RegistrationResponseDto create(TrainerCreateDto dto);

    void login(LoginRequestDto dto);

    TrainerDto getTrainerByUsername(String username);

    void changePassword(ChangeLoginDto dto);

    TrainerDto update(TrainerUpdateDto dto, String username);

    void activateOrDeactivateTrainer(String username);

    List<TrainingDto> getTrainerTrainingsList(String trainerUsername, Date fromDate, Date toDate, String traineeName);
}
