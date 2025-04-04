package epam.com.task_rest_boot.mapper;

import epam.com.task_rest_boot.dto.training_type.TrainingTypeDto;
import epam.com.task_rest_boot.entity.TrainingType;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TrainingTypeMapper {
    public TrainingTypeDto toDto(TrainingType trainingType) {
        TrainingTypeDto trainingTypeDto = new TrainingTypeDto(trainingType.getId(), trainingType.getTrainingTypeName());
        return trainingTypeDto;
    }

    public List<TrainingTypeDto> toDtoList(List<TrainingType> trainingTypes) {
        return trainingTypes.stream().map(this::toDto).toList();
    }
}
