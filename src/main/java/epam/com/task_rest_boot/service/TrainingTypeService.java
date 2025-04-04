package epam.com.task_rest_boot.service;

import epam.com.task_rest_boot.dto.training_type.TrainingTypeDto;
import epam.com.task_rest_boot.entity.TrainingType;

import java.util.List;

public interface TrainingTypeService {

    TrainingType getTrainingTypeById(Integer id);

    List<TrainingTypeDto> getAllTrainingTypes();
}
