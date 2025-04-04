package epam.com.task_rest_boot.service.impl;


import epam.com.task_rest_boot.dto.ChangeLoginDto;
import epam.com.task_rest_boot.dto.LoginRequestDto;
import epam.com.task_rest_boot.dto.RegistrationResponseDto;
import epam.com.task_rest_boot.dto.UpdateTraineeTrainersListDto;
import epam.com.task_rest_boot.dto.trainee.TraineeCreateDto;
import epam.com.task_rest_boot.dto.trainee.TraineeDto;
import epam.com.task_rest_boot.dto.trainee.TraineeUpdateDto;
import epam.com.task_rest_boot.dto.trainer.TrainerShortDto;
import epam.com.task_rest_boot.dto.training.TrainingDto;
import epam.com.task_rest_boot.entity.Trainee;
import epam.com.task_rest_boot.entity.Trainer;
import epam.com.task_rest_boot.entity.Training;
import epam.com.task_rest_boot.entity.User;
import epam.com.task_rest_boot.exception.AuthenticationException;
import epam.com.task_rest_boot.exception.ResourceNotFoundException;
import epam.com.task_rest_boot.mapper.TraineeMapper;
import epam.com.task_rest_boot.mapper.TrainerMapper;
import epam.com.task_rest_boot.mapper.TrainingMapper;
import epam.com.task_rest_boot.repository.TraineeRepository;
import epam.com.task_rest_boot.repository.TrainerRepository;
import epam.com.task_rest_boot.repository.TrainingRepository;
import epam.com.task_rest_boot.service.TraineeService;
import epam.com.task_rest_boot.util.UsernamePasswordGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class TraineeServiceImpl implements TraineeService {
    private final TraineeRepository traineeRepository;
    private final TraineeMapper traineeMapper;
    private final TrainerMapper trainerMapper;
    private final UsernamePasswordGenerator usernamePasswordGenerator;
    private final TrainingRepository trainingRepository;
    private final TrainingMapper trainingMapper;
    private final TrainerRepository trainerRepository;

    @Override
    public RegistrationResponseDto create(TraineeCreateDto traineeCreateDto) {
        Trainee trainee = traineeMapper.toEntity(traineeCreateDto);
        User user = trainee.getUser();
        String username = usernamePasswordGenerator
                .generateUsername(traineeCreateDto.firstName(), traineeCreateDto.lastName());
        String password = usernamePasswordGenerator.generatePassword();
        user.setPassword(password);
        user.setUserName(username);
        traineeRepository.save(trainee);
        RegistrationResponseDto registrationResponseDto = new RegistrationResponseDto(username, password);
        return registrationResponseDto;
    }

    @Override
    public void login(LoginRequestDto dto) {
        if (!traineeRepository.checkUsernameAndPasswordMatch(dto.username(), dto.password())) {
            log.warn("Login failed !");
            throw new AuthenticationException("Username or password is incorrect");
        }
    }

    @Override
    public TraineeDto getTraineeByUsername(String username) {
        Trainee trainee = traineeRepository.findTraineeByUser_UserName(username)
                .orElseThrow(() ->{
                    log.warn("Trainee not found with username {}", username);
                    return new ResourceNotFoundException("Trainee not found");});
        return traineeMapper.toDto(trainee);
    }

    @Override
    public void changePassword(ChangeLoginDto dto) {
        if (!traineeRepository.checkUsernameAndPasswordMatch(dto.username(), dto.oldPassword())) {
            log.warn("Password change failed");
            throw new AuthenticationException("username or password is incorrect");
        }
        traineeRepository.changePassword(dto.username(), dto.newPassword());
    }

    @Override
    public TraineeDto update(String username, TraineeUpdateDto traineeUpdateDto) {
        Trainee trainee = traineeRepository.findTraineeByUser_UserName(username)
                .orElseThrow(() -> new ResourceNotFoundException("Trainee not found"));
        Trainee trainee1 = traineeMapper.partialUpdate(traineeUpdateDto, trainee);
        Trainee updatedTrainee = traineeRepository.save(trainee1);
        return traineeMapper.toDto(updatedTrainee);
    }

    @Override
    public void activateOrDeactivateTrainee(String username) {
        if (!traineeRepository.existsByUser_UserName(username)) {
            log.warn("Trainee not found with username for activation/deactivation : {}", username);
            throw new ResourceNotFoundException("Trainee not found");
        }
        traineeRepository.activateOrDeactivateTrainee(username);
    }

    @Override
    public void deleteTraineeByUsername(String username) {
        if (!traineeRepository.existsByUser_UserName(username)) {
            log.warn("Trainee not found for deletion with username : {}", username);
            throw new ResourceNotFoundException("Trainee not found");
        }
        traineeRepository.deleteByUser_UserName(username);
    }

    @Override
    public List<TrainingDto> getTraineeTrainingsList(String traineeUsername, Date fromDate, Date toDate, String trainerName, Integer trainingTypeId) {
        if (!traineeRepository.existsByUser_UserName(traineeUsername)) {
            log.warn("Trainee not found for deletion : {}", traineeUsername);
            throw new ResourceNotFoundException("Trainee not found");
        }
        List<Training> trainings = trainingRepository.getTraineeTrainingsListByTraineeUsernameAndCriteria(traineeUsername, fromDate, toDate,
                                                                                                          trainerName, trainingTypeId);
        return trainingMapper.toDtoList(trainings);
    }

    @Override
    public List<TrainerShortDto> getTrainersListNotAssignedOnTrainee(String traineeUsername) {
        Integer traineeId = traineeRepository.findTraineeIdByUsername(traineeUsername)
                .orElseThrow(() ->{
                    log.warn("Trainee not found : {}", traineeUsername);
                    return new ResourceNotFoundException("Trainee not found");
                });
        List<Integer> notAssignedTrainerIds = trainerRepository.findTrainersIdNotAssignedOnTraineeByTraineeId(traineeId);
        List<Trainer> notAssignedTrainers = trainerRepository.findTrainersById(notAssignedTrainerIds);
        return trainerMapper.toShortDtoList(notAssignedTrainers);
    }

    @Override
    public List<TrainerShortDto> updateTraineeTrainerList(UpdateTraineeTrainersListDto dto) {
        Trainee trainee = traineeRepository.findTraineeByUser_UserName(dto.traineeUsername())
                .orElseThrow(() ->{
                    log.warn("Trainee not found : {}", dto.traineeUsername());
                    return new ResourceNotFoundException("Trainee not found");
                });
        List<Trainer> trainers = trainerRepository.findTrainersByUser_UserNameIn(dto.trainers());
        trainee.setTrainers(trainers);
        traineeRepository.save(trainee);
        return trainerMapper.toShortDtoList(trainers);
    }

}
