package epam.com.task_rest_boot.service.impl;


import epam.com.task_rest_boot.dto.ChangeLoginDto;
import epam.com.task_rest_boot.dto.LoginRequestDto;
import epam.com.task_rest_boot.dto.RegistrationResponseDto;
import epam.com.task_rest_boot.dto.trainer.*;
import epam.com.task_rest_boot.dto.training.TrainingDto;
import epam.com.task_rest_boot.entity.Trainer;
import epam.com.task_rest_boot.entity.Training;
import epam.com.task_rest_boot.entity.User;
import epam.com.task_rest_boot.exception.AuthenticationException;
import epam.com.task_rest_boot.exception.ResourceNotFoundException;
import epam.com.task_rest_boot.mapper.TrainerMapper;
import epam.com.task_rest_boot.mapper.TrainingMapper;
import epam.com.task_rest_boot.repository.TrainerRepository;
import epam.com.task_rest_boot.repository.TrainingRepository;
import epam.com.task_rest_boot.service.TrainerService;
import epam.com.task_rest_boot.util.UsernamePasswordGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class TrainerServiceImpl implements TrainerService {
    private final TrainerRepository trainerRepository;
    private final TrainerMapper trainerMapper;
    private final UsernamePasswordGenerator usernamePasswordGenerator;
    private final TrainingRepository trainingRepository;
    private final TrainingMapper trainingMapper;

    public RegistrationResponseDto create(TrainerCreateDto dto){
        Trainer trainer = trainerMapper.toEntity(dto);
        String username = usernamePasswordGenerator
                .generateUsername(dto.firstName(), dto.lastName());
        String password = usernamePasswordGenerator.generatePassword();
        User user = trainer.getUser();
        user.setUserName(username);
        user.setPassword(password);
        trainerRepository.save(trainer);
        return new RegistrationResponseDto(username, password);
    }

    @Override
    public void login(LoginRequestDto dto){
        if (!trainerRepository.checkUsernameAndPasswordMatch(dto.username(), dto.password())){
            throw new AuthenticationException("username or password is incorrect!");
        }
    }

    @Override
    public TrainerDto getTrainerByUsername(String username) {
        Trainer trainer = trainerRepository.findTrainerByUser_UserName(username)
                .orElseThrow(() -> {
                    log.warn("Trainee not found with username : {}", username);
                    return new ResourceNotFoundException("Trainer not found");});
        return trainerMapper.toDto(trainer);
    }

    @Override
    public void changePassword(ChangeLoginDto dto) {
        if (!trainerRepository.checkUsernameAndPasswordMatch(dto.username(), dto.oldPassword())) {
            log.warn("Login failed !");
            throw new AuthenticationException("username or password is incorrect");
        }
        trainerRepository.changePassword(dto.username(), dto.newPassword());
    }

    @Override
    public TrainerDto update(TrainerUpdateDto dto, String username){
        Trainer trainer = trainerRepository.findTrainerByUser_UserName(username)
                .orElseThrow(() ->{
                    log.warn("Trainer not found with username : {}", username);
                    return new ResourceNotFoundException("Trainer not found");
                });
        Trainer trainer1 = trainerMapper.partialUpdate(dto, trainer);
        Trainer updatedTrainer = trainerRepository.save(trainer1);
        return trainerMapper.toDto(updatedTrainer);
    }

    @Override
    public void activateOrDeactivateTrainer(String username){
        if (!trainerRepository.existsByUser_UserName(username)) {
            log.warn("Trainer not found with username : {}", username);
            throw new ResourceNotFoundException("Trainer not found");
        }
        trainerRepository.activateOrDeactivateTrainer(username);
    }

    @Override
    public List<TrainingDto> getTrainerTrainingsList(String trainerUsername, Date fromDate, Date toDate, String traineeName) {
        if (!trainerRepository.existsByUser_UserName(trainerUsername)) {
            log.warn("Trainer not found with username : {}", trainerUsername);
            throw new ResourceNotFoundException("Trainer not found");
        }
        List<Training> trainings = trainingRepository.getTrainerTrainingsListByTrainerUsernameAndCriteria(trainerUsername, fromDate, toDate, traineeName);
        return trainingMapper.toDtoList(trainings);
    }

}
