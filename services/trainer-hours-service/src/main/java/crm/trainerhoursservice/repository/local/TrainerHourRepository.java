package crm.trainerhoursservice.repository.local;

import crm.trainerhoursservice.model.entity.dev.TrainingHour;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TrainerHourRepository extends JpaRepository<TrainingHour, Integer> {

    Optional<TrainingHour> findByTrainerUsername(String trainerUsername);

}
