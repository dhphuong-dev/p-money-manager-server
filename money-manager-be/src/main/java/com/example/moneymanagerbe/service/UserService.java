package com.example.moneymanagerbe.service;

import com.example.moneymanagerbe.domain.dto.pagination.PaginationFullRequestDto;
import com.example.moneymanagerbe.domain.dto.pagination.PaginationResponseDto;
import com.example.moneymanagerbe.domain.dto.request.UserUpdateDto;
import com.example.moneymanagerbe.domain.dto.response.UserDto;
import com.example.moneymanagerbe.domain.entity.User;
import com.example.moneymanagerbe.security.UserPrincipal;

public interface UserService {

  UserDto getUserDtoById(String userId);

  User getUserById(String id);

  PaginationResponseDto<UserDto> getCustomers(PaginationFullRequestDto request);

  UserDto getCurrentUser(UserPrincipal principal);

  UserDto updateProfile(String id, UserUpdateDto userUpdateDto);

}
