package epam.com.task_rest_boot.service.impl;

import epam.com.task_rest_boot.dto.training.TrainingCreateDto;
import epam.com.task_rest_boot.entity.Trainee;
import epam.com.task_rest_boot.entity.Trainer;
import epam.com.task_rest_boot.entity.Training;
import epam.com.task_rest_boot.entity.TrainingType;
import epam.com.task_rest_boot.exception.ResourceNotFoundException;
import epam.com.task_rest_boot.mapper.TrainingMapper;
import epam.com.task_rest_boot.repository.TraineeRepository;
import epam.com.task_rest_boot.repository.TrainerRepository;
import epam.com.task_rest_boot.repository.TrainingRepository;
import epam.com.task_rest_boot.repository.TrainingTypeRepository;
import epam.com.task_rest_boot.service.TrainingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TrainingServiceImpl implements TrainingService {
    private final TrainingRepository trainingRepository;
    private final TrainerRepository trainerRepository;
    private final TraineeRepository traineeRepository;
    private final TrainingTypeRepository trainingTypeRepository;
    private final TrainingMapper trainingMapper;

    @Override
    public void create(TrainingCreateDto dto) {
        Trainer trainer = trainerRepository.findTrainerByUser_UserName(dto.trainerUsername())
                .orElseThrow(() -> new ResourceNotFoundException("Trainer not found"));
        Trainee trainee = traineeRepository.findTraineeByUser_UserName(dto.traineeUsername())
                .orElseThrow(() -> new ResourceNotFoundException("Trainee not found"));
        TrainingType trainingType = trainingTypeRepository.findTrainingTypeById(dto.trainingTypeId())
                .orElseThrow(() -> new ResourceNotFoundException("Training type not found"));
        Training training = trainingMapper.toEntity(dto, trainer, trainee, trainingType);
        trainingRepository.save(training);
    }
}
