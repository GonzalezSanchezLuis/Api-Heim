package com.holi.api.users.application.mapper;

import com.holi.api.users.application.dto.UserRequest;
import com.holi.api.users.application.dto.UserResponse;
import com.holi.api.users.domain.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(source = "userId", target = "userId")
    UserResponse toResponse(User user);
    User toEntity(UserRequest userRequest);

}
