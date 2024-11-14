package com.holi.api.drivers.infraestructure.repository;

import com.holi.api.drivers.domain.entity.Driver;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface DriverRepository extends CrudRepository<Driver, Long> {
    Optional<Driver> findDriverByEmail(String email);
}
