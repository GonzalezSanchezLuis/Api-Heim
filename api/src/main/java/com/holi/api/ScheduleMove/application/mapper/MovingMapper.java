package com.holi.api.ScheduleMove.application.mapper;

import com.holi.api.drivers.application.dto.DriverResponse;
import com.holi.api.ScheduleMove.application.dto.MovingRequest;
import com.holi.api.ScheduleMove.application.dto.MovingResponse;
import com.holi.api.ScheduleMove.application.mapper.helper.MovingMapperHelper;
import com.holi.api.ScheduleMove.domain.entity.ReservationMoving;
import com.holi.api.users.application.dto.UserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = MovingMapperHelper.class)
public interface MovingMapper {


        @Mapping(target = "reservationMovingId", ignore = true) // Se ignora porque es autogenerado
        @Mapping(target = "user", source = "userId", qualifiedByName = "mapUser")
        @Mapping(target = "driver", source = "driverId", qualifiedByName = "mapDriver")
        ReservationMoving toEntity(MovingRequest movingRequest);

        @Mapping(target = "reservationMovingId", source = "reservationMovingId")
        @Mapping(target = "userId", source = "user.userId")
        @Mapping(target = "userResponse", expression = "java(mapUserToResponse(reservationMoving.getUser()))")
        @Mapping(target = "driverResponse", expression = "java(mapDriverToResponse(reservationMoving.getDriver()))")
        @Mapping(target = "status", source = "status")
        @Mapping(target = "moveType", source = "moveType")
        MovingResponse toResponse(ReservationMoving reservationMoving);

        default UserResponse mapUserToResponse(com.holi.api.users.domain.entity.User user) {
            if (user == null) return null;
            return new UserResponse(
                    user.getUserId(),
                    user.getFullName(),
                    user.getEmail(),
                    user.getPhone(),
                    user.getDocument(),
                    user.getUrlAvatarProfile(),
                    user.getRole(),
                    user.getCreatedAt().toString(),
                    user.isActive(), null);
        }

        default DriverResponse mapDriverToResponse(com.holi.api.drivers.domain.entity.Driver driver) {
            if (driver == null) return null;
            return new DriverResponse(
                    driver.getDriverId(),
                    driver.getFullName(),
                    driver.getEmail(),
                    driver.getPhone(),
                    driver.getUrlAvatarProfile(),
                    driver.getDocument(),
                    driver.getLicenseNumber(),
                    driver.getVehicleType(),
                    driver.getEnrollVehicle(),
                    driver.getRole(),
                    driver.getCreatedAt(),
                    driver.isActive(), null);
        }

        @Mapping(target = "userId", source = "userId")
        default com.holi.api.users.domain.entity.User mapUser(Long userId) {
            if (userId == null) return null;
            com.holi.api.users.domain.entity.User user = new com.holi.api.users.domain.entity.User();
            user.setUserId(userId);
            return user;
        }

        @Mapping(target = "driverId", source = "driverId")
        default com.holi.api.drivers.domain.entity.Driver mapDriver(Long driverId) {
            if (driverId == null) return null;
            com.holi.api.drivers.domain.entity.Driver driver = new com.holi.api.drivers.domain.entity.Driver();
            driver.setDriverId(driverId);
            return driver;
        }
    }

