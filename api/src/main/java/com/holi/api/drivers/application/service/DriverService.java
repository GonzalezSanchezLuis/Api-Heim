package com.holi.api.drivers.application.service;

import com.holi.api.drivers.application.dto.DriverRequest;
import com.holi.api.drivers.application.dto.DriverResponse;
import com.holi.api.drivers.application.mapper.DriverMapper;
import com.holi.api.drivers.domain.entity.Driver;
import com.holi.api.drivers.infraestructure.repository.DriverRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class DriverService {
    private final DriverRepository driverRepository;
    private final DriverMapper driverMapper;
    private final BCryptPasswordEncoder bcryptPasswordEncoder;

    public DriverService(DriverRepository driverRepository, DriverMapper driverMapper, BCryptPasswordEncoder bCryptPasswordEncoder){
        this.driverMapper = driverMapper;
        this.driverRepository = driverRepository;
        this.bcryptPasswordEncoder = bCryptPasswordEncoder;

    }

    public DriverResponse registerDriver(DriverRequest driverRequest) throws Exception {
        Optional<Driver> existingDriver = driverRepository.findDriverByEmail(driverRequest.getEmail());
        if (existingDriver.isPresent()) {
            throw new Exception("Ya existe un usuario registrado con ese email.");
        } else {
            String encodePassword = bcryptPasswordEncoder.encode(driverRequest.getPassword());
            driverRequest.setPassword(encodePassword);
            Driver newDriver = driverMapper.toEntity(driverRequest);
            newDriver.setActive(false);
            newDriver.setRole("DRIVER");

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
        if(driverRequest.getPassword() != null && !driverRequest.getPassword().isEmpty()){
            driver.setPassword(bcryptPasswordEncoder.encode(driverRequest.getPassword()));
        }
        driver.setFullName(driverRequest.getFullName());
        driver.setEmail(driverRequest.getEmail());
        driver.setPhone(driverRequest.getPhone());
        driver.setVehicleType(driverRequest.getVehicleType());
        driver.setEnrollVehicle(driverRequest.getEnrollVehicle());
        driver.setDocument(driverRequest.getDocument());
        driver.setUrlAvatarProfile(driverRequest.getUrlAvatarProfile());


        driverRepository.save(driver);
        return driverMapper.toResponse(driver);
    }


    public void driverDelete(Long driverId){
        Driver driverToDelete = driverRepository.findById(driverId).orElseThrow(() -> new NoSuchElementException("Usuario no encontrado"));
        driverRepository.delete(driverToDelete);

    }

    public Driver updateDriverStatus(Long driverId, DriverRequest statusRequest ){
        Driver driver = driverRepository.findById(driverId).orElseThrow(()->  new RuntimeException("Driver not found"));
        driver.setStatus(statusRequest.getStatus());
        return driverRepository.save(driver);
    }
}
