package com.example.moneymanagerbe.domain.mapper;

import com.example.moneymanagerbe.domain.dto.request.UserCreateDto;
import com.example.moneymanagerbe.domain.dto.response.RegisterResponseDto;
import com.example.moneymanagerbe.domain.dto.response.UserDto;
import com.example.moneymanagerbe.domain.entity.User;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

  User toUser(UserCreateDto userCreateDTO);

  @Mappings({
      @Mapping(target = "roleName", source = "user.role.name"),
  })
  UserDto toUserDto(User user);

  RegisterResponseDto toRegisterResponseDto(User user);

  List<UserDto> toUserDtos(List<User> user);

}
