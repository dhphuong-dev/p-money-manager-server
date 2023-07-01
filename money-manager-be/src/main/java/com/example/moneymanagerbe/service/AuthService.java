package com.example.moneymanagerbe.service;

import com.example.moneymanagerbe.domain.dto.request.ForgotPasswordRequestDto;
import com.example.moneymanagerbe.domain.dto.request.LoginRequestDto;
import com.example.moneymanagerbe.domain.dto.request.TokenRefreshRequestDto;
import com.example.moneymanagerbe.domain.dto.request.UserCreateDto;
import com.example.moneymanagerbe.domain.dto.response.CommonResponseDto;
import com.example.moneymanagerbe.domain.dto.response.LoginResponseDto;
import com.example.moneymanagerbe.domain.dto.response.RegisterResponseDto;
import com.example.moneymanagerbe.domain.dto.response.TokenRefreshResponseDto;
import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface AuthService {

  LoginResponseDto login(LoginRequestDto request);

  RegisterResponseDto register(UserCreateDto request);

  TokenRefreshResponseDto refresh(TokenRefreshRequestDto request);

  CommonResponseDto logout(HttpServletRequest request,
                           HttpServletResponse response, Authentication authentication);

  CommonResponseDto forgotPassword(ForgotPasswordRequestDto requestDto);

}
