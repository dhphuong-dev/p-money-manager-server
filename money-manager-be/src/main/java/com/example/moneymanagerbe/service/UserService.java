package com.example.moneymanagerbe.service;

import com.example.moneymanagerbe.domain.dto.pagination.PaginationFullRequestDto;
import com.example.moneymanagerbe.domain.dto.pagination.PaginationResponseDto;
import com.example.moneymanagerbe.domain.dto.response.UserDto;
import com.example.moneymanagerbe.security.UserPrincipal;

public interface UserService {

  UserDto getUserById(String userId);

  PaginationResponseDto<UserDto> getCustomers(PaginationFullRequestDto request);

  UserDto getCurrentUser(UserPrincipal principal);

}
