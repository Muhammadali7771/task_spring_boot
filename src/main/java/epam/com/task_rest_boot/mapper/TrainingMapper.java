package epam.com.task_rest_boot.mapper;

import epam.com.task_rest_boot.dto.training.TrainingCreateDto;
import epam.com.task_rest_boot.dto.training.TrainingDto;
import epam.com.task_rest_boot.entity.Trainee;
import epam.com.task_rest_boot.entity.Trainer;
import epam.com.task_rest_boot.entity.Training;
import epam.com.task_rest_boot.entity.TrainingType;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TrainingMapper {

    public Training toEntity(TrainingCreateDto dto, Trainer trainer, Trainee trainee, TrainingType trainingType) {
        if (dto == null) {
            return null;
        }
        Training training = new Training();
        training.setTrainer(trainer);
        training.setTrainee(trainee);
        training.setTrainingType(trainingType);
        training.setTrainingDate(dto.trainingDate());
        training.setTrainingName(dto.trainingName());
        training.setDuration(dto.trainingDuration());
        return training;
    }

    public TrainingDto toDto(Training training) {
        if (training == null) {
            return null;
        }
        TrainingDto trainingDto = new TrainingDto(training.getTrainingName(),
                                                  training.getTrainingDate(),
                                                  training.getTrainingType().getId(),
                                                  training.getDuration(),
                                                  training.getTrainee().getUser().getUserName(),
                                                  training.getTrainer().getUser().getUserName());
        return trainingDto;
    }

    public List<TrainingDto> toDtoList(List<Training> trainings) {
        return trainings.stream().map(this::toDto).collect(Collectors.toList());
    }
}
