package com.holi.api.ScheduleMove.infraestructure.repository;

import com.holi.api.ScheduleMove.domain.entity.ReservationMoving;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface ReservationMovingRepository extends CrudRepository<ReservationMoving, Long> {
    Optional<ReservationMoving> findById(Long id);
}
