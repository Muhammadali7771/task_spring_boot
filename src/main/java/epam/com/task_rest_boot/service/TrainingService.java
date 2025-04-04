package epam.com.task_rest_boot.service;

import epam.com.task_rest_boot.dto.training.TrainingCreateDto;

public interface TrainingService {
    void create(TrainingCreateDto dto);
}
