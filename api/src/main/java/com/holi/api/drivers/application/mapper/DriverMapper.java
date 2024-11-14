package com.holi.api.drivers.application.mapper;

import com.holi.api.drivers.application.dto.DriverRequest;
import com.holi.api.drivers.application.dto.DriverResponse;
import com.holi.api.drivers.domain.entity.Driver;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DriverMapper {
    DriverResponse toResponse(Driver driver);
    Driver toEntity(DriverRequest driverRequest);
}
