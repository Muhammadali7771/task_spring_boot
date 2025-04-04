package epam.com.task_rest_boot.repository;

import epam.com.task_rest_boot.entity.TrainingType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TrainingTypeRepository extends JpaRepository<TrainingType, Integer> {
    Optional<TrainingType> findTrainingTypeById(Integer id);

}
