package com.example.moneymanagerbe.service;

import com.example.moneymanagerbe.domain.dto.request.LoginRequestDto;
import com.example.moneymanagerbe.domain.dto.request.TokenRefreshRequestDto;
import com.example.moneymanagerbe.domain.dto.request.UserCreateDto;
import com.example.moneymanagerbe.domain.dto.response.CommonResponseDto;
import com.example.moneymanagerbe.domain.dto.response.LoginResponseDto;
import com.example.moneymanagerbe.domain.dto.response.RegisterResponseDto;
import com.example.moneymanagerbe.domain.dto.response.TokenRefreshResponseDto;

import javax.servlet.http.HttpServletRequest;

public interface AuthService {

  LoginResponseDto login(LoginRequestDto request);

  RegisterResponseDto register(UserCreateDto request);

  TokenRefreshResponseDto refresh(TokenRefreshRequestDto request);

  CommonResponseDto logout(HttpServletRequest request);

}
