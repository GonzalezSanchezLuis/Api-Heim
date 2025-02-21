package com.holi.api.ScheduleMove.application.service;

import com.holi.api.drivers.domain.entity.Driver;
import com.holi.api.drivers.infraestructure.repository.DriverRepository;
import com.holi.api.ScheduleMove.application.dto.MovingRequest;
import com.holi.api.ScheduleMove.application.dto.MovingResponse;
import com.holi.api.ScheduleMove.application.mapper.MovingMapper;
import com.holi.api.ScheduleMove.domain.entity.ReservationMoving;
import com.holi.api.ScheduleMove.infraestructure.repository.ReservationMovingRepository;
import com.holi.api.pricing.service.PricingService;
import com.holi.api.users.domain.entity.User;
import com.holi.api.users.infraestructure.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@Transactional
public class ReservationMovingService {
    private final ReservationMovingRepository reservationMovingRepository;
    private final MovingMapper movingMapper;
    private final UserRepository userRepository;
    private final DriverRepository  driverRepository;
    private final PricingService pricingService;


    @Autowired
    public  ReservationMovingService(
            ReservationMovingRepository  reservationMovingRepository,
            MovingMapper movingMapper,
            UserRepository userRepository,
            DriverRepository driverRepository,
            PricingService pricingService){

        this.reservationMovingRepository = reservationMovingRepository;
        this.movingMapper = movingMapper;
        this.userRepository = userRepository;
        this.driverRepository = driverRepository;
        this.pricingService = pricingService;
    }

    public MovingResponse savedReservationMoving(MovingRequest movingRequest){
        ReservationMoving newReservationMoving = movingMapper.toEntity(movingRequest);

        User user = userRepository.findById(movingRequest.getUserId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Driver driver = driverRepository.findById(movingRequest.getDriverId())
                .orElseThrow(() -> new RuntimeException("Conductor  no encontrado"));

        newReservationMoving.setUser(user);
        newReservationMoving.setDriver(driver);
        
        ReservationMoving savedReservation = reservationMovingRepository.save(newReservationMoving);
        System.out.println("DATOS DEL CODUCTOR" + driver);
        return movingMapper.toResponse(savedReservation);

    }

    public MovingResponse reservationMovingById(Long reservationMovingId) throws NoSuchElementException {
        ReservationMoving reservationMoving = reservationMovingRepository.findById(reservationMovingId).orElseThrow(()
                -> new NoSuchElementException("Información no encontrada"));

        return  movingMapper.toResponse(reservationMoving);

    }


}
