package com.urbanhoney.backend.usecase.dto.mapper;

import java.util.ArrayList;

import com.urbanhoney.backend.models.UserEntity;
import com.urbanhoney.backend.usecase.dto.UserDto;

public class UserMapper {
    public static UserDto mapToUserDto(UserEntity userEntity) {
        return new UserDto(
            userEntity.getId(),
            userEntity.getEmail(),
            userEntity.getUsername(),
            userEntity.getPassword(),
            userEntity.getProfilePicture(),
            userEntity.getIsAdmin());
    }

    public static UserEntity mapToUser(UserDto userDto) {
        return new UserEntity(
            userDto.getId(),
            userDto.getEmail(),
            userDto.getUsername(),
            userDto.getPassword(),
            userDto.getProfilePicture(),
            userDto.getIs_admin(),
            new ArrayList<>());
    }
}
