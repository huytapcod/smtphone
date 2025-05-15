package com.hqshop.ecommerce.multivendor1.mapper;

import com.hqshop.ecommerce.multivendor1.dto.request.UserCreationRequest;
import com.hqshop.ecommerce.multivendor1.dto.request.UserUpdateRequest;
import com.hqshop.ecommerce.multivendor1.dto.response.UserResponse;
import com.hqshop.ecommerce.multivendor1.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser (UserCreationRequest request);
    UserResponse toUserResponse (User user);
    void updateUser(@MappingTarget User user, UserUpdateRequest request);
}
