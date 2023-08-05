package com.example.moneymanagerbe.service;

import com.example.moneymanagerbe.domain.dto.request.ChangePasswordRequestDto;
import com.example.moneymanagerbe.domain.dto.request.UserUpdateDto;
import com.example.moneymanagerbe.domain.dto.response.CommonResponseDto;
import com.example.moneymanagerbe.domain.dto.response.UserDto;
import com.example.moneymanagerbe.domain.entity.User;
import com.example.moneymanagerbe.security.UserPrincipal;

public interface UserService {

  User getUserById(String id);

  UserDto getCurrentUser(UserPrincipal principal);

  UserDto updateProfile(String id, UserUpdateDto userUpdateDto);

  CommonResponseDto changePassword(String userId, ChangePasswordRequestDto request);

}
