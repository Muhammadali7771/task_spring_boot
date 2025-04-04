package epam.com.task_rest_boot.service.impl;


import epam.com.task_rest_boot.dto.training_type.TrainingTypeDto;
import epam.com.task_rest_boot.entity.TrainingType;
import epam.com.task_rest_boot.exception.ResourceNotFoundException;
import epam.com.task_rest_boot.mapper.TrainingTypeMapper;
import epam.com.task_rest_boot.repository.TrainingTypeRepository;
import epam.com.task_rest_boot.service.TrainingTypeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class TrainingTypeServiceImpl implements TrainingTypeService {
    private final TrainingTypeRepository trainingTypeRepository;
    private final TrainingTypeMapper trainingTypeMapper;

    public TrainingTypeServiceImpl(TrainingTypeRepository trainingTypeRepository, TrainingTypeMapper trainingTypeMapper) {
        this.trainingTypeRepository = trainingTypeRepository;
        this.trainingTypeMapper = trainingTypeMapper;
    }

    @Override
    public TrainingType getTrainingTypeById(Integer id) {
        return trainingTypeRepository.findTrainingTypeById(id).orElseThrow(
                 () -> {
                     log.warn("Training type not found with id : {}", id);
                     return new ResourceNotFoundException("Training type not found");
                 });
    }

    @Override
    public List<TrainingTypeDto> getAllTrainingTypes() {
        List<TrainingType> allTrainingTypes = trainingTypeRepository.findAll();
        return trainingTypeMapper.toDtoList(allTrainingTypes);
    }
}
