package epam.com.task_rest_boot.service;

import epam.com.task_rest_boot.dto.ChangeLoginDto;
import epam.com.task_rest_boot.dto.LoginRequestDto;
import epam.com.task_rest_boot.dto.RegistrationResponseDto;
import epam.com.task_rest_boot.dto.UpdateTraineeTrainersListDto;
import epam.com.task_rest_boot.dto.trainee.TraineeCreateDto;
import epam.com.task_rest_boot.dto.trainee.TraineeDto;
import epam.com.task_rest_boot.dto.trainee.TraineeUpdateDto;
import epam.com.task_rest_boot.dto.trainer.TrainerShortDto;
import epam.com.task_rest_boot.dto.training.TrainingDto;

import java.util.Date;
import java.util.List;

public interface TraineeService {
    RegistrationResponseDto create(TraineeCreateDto traineeCreateDto);

    void login(LoginRequestDto dto);


    TraineeDto getTraineeByUsername(String username);

    void changePassword(ChangeLoginDto dto);

    TraineeDto update(String username, TraineeUpdateDto traineeUpdateDto);

    void activateOrDeactivateTrainee(String username);

    void deleteTraineeByUsername(String username);

    List<TrainingDto> getTraineeTrainingsList(String traineeUsername, Date fromDate, Date toDate, String trainerName, Integer trainingTypeId);

    List<TrainerShortDto> getTrainersListNotAssignedOnTrainee(String traineeUsername);

    List<TrainerShortDto> updateTraineeTrainerList(UpdateTraineeTrainersListDto dto);
}
