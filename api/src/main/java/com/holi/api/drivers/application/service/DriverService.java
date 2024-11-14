package com.holi.api.drivers.application.service;

import com.holi.api.drivers.application.dto.DriverRequest;
import com.holi.api.drivers.application.dto.DriverResponse;
import com.holi.api.drivers.application.mapper.DriverMapper;
import com.holi.api.drivers.domain.entity.Driver;
import com.holi.api.drivers.infraestructure.repository.DriverRepository;
import com.holi.api.users.application.dto.UserRequest;
import com.holi.api.users.application.dto.UserResponse;
import com.holi.api.users.domain.entity.User;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class DriverService {
    private final DriverRepository driverRepository;
    private final DriverMapper driverMapper;

    public DriverService(DriverRepository driverRepository, DriverMapper driverMapper){
        this.driverMapper = driverMapper;
        this.driverRepository = driverRepository;

    }

    public DriverResponse registerDriver(DriverRequest driverRequest) throws Exception {
        Optional<Driver> existingDriver = driverRepository.findDriverByEmail(driverRequest.getEmail());
        if (existingDriver.isPresent()) {
            throw new Exception("Ya existe un usuario registrado con ese email.");
        } else {
            Driver newDriver = driverMapper.toEntity(driverRequest);
            newDriver.setActive(false);

            System.out.println("Mapped driver: " + newDriver);
            return driverMapper.toResponse(driverRepository.save(newDriver));

        }
    }

    public DriverResponse getDriverById(Long driverId) throws NoSuchElementException {
        Driver driver = driverRepository.findById(driverId).orElseThrow(() -> new NoSuchElementException("Usuario no encontrado"));
        return driverMapper.toResponse(driver);
    }

    public DriverResponse updatedDriverData(Long driverId, DriverRequest driverRequest) {
        // Buscar el usuario por ID
        Driver driver = driverRepository.findById(driverId).orElseThrow(() -> new NoSuchElementException("Usuario no encontrado"));
        driver.setFullName(driverRequest.getFullName());
        driver.setEmail(driverRequest.getEmail());
        driver.setPhone(driverRequest.getPhone());
        driver.setPassword(driverRequest.getPassword());
        driver.setVehicleType(driverRequest.getVehicleType());
        driverRepository.save(driver);

        return driverMapper.toResponse(driver);
    }


    public void driverDelete(Long driverId){
        Driver driverToDelete = driverRepository.findById(driverId).orElseThrow(() -> new NoSuchElementException("Usuario no encontrado"));
        driverRepository.delete(driverToDelete);

    }
}
