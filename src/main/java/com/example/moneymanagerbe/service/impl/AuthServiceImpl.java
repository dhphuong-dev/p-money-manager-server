package com.example.moneymanagerbe.service.impl;

import com.example.moneymanagerbe.constant.*;
import com.example.moneymanagerbe.domain.dto.common.DataMailDto;
import com.example.moneymanagerbe.domain.dto.request.ForgotPasswordRequestDto;
import com.example.moneymanagerbe.domain.dto.request.LoginRequestDto;
import com.example.moneymanagerbe.domain.dto.request.TokenRefreshRequestDto;
import com.example.moneymanagerbe.domain.dto.request.UserCreateDto;
import com.example.moneymanagerbe.domain.dto.response.CommonResponseDto;
import com.example.moneymanagerbe.domain.dto.response.LoginResponseDto;
import com.example.moneymanagerbe.domain.dto.response.RegisterResponseDto;
import com.example.moneymanagerbe.domain.dto.response.TokenRefreshResponseDto;
import com.example.moneymanagerbe.domain.entity.Category;
import com.example.moneymanagerbe.domain.entity.Wallet;
import com.example.moneymanagerbe.domain.entity.User;
import com.example.moneymanagerbe.domain.mapper.UserMapper;
import com.example.moneymanagerbe.exception.AlreadyExistException;
import com.example.moneymanagerbe.exception.NotFoundException;
import com.example.moneymanagerbe.exception.UnauthorizedException;
import com.example.moneymanagerbe.repository.CategoryRepository;
import com.example.moneymanagerbe.repository.WalletRepository;
import com.example.moneymanagerbe.repository.RoleRepository;
import com.example.moneymanagerbe.repository.UserRepository;
import com.example.moneymanagerbe.security.UserPrincipal;
import com.example.moneymanagerbe.security.jwt.JwtTokenProvider;
import com.example.moneymanagerbe.service.AuthService;
import com.example.moneymanagerbe.util.RandomString;
import com.example.moneymanagerbe.util.SendMailUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

  private final AuthenticationManager authenticationManager;

  private final JwtTokenProvider jwtTokenProvider;

  private final PasswordEncoder passwordEncoder;

  private final UserRepository userRepository;

  private final RoleRepository roleRepository;

  private final WalletRepository walletRepository;

  private final CategoryRepository categoryRepository;

  private final UserMapper userMapper;

  private final SendMailUtil sendMailUtil;

  @Override
  public LoginResponseDto login(LoginRequestDto request) {
    try {
      Authentication authentication = authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
      SecurityContextHolder.getContext().setAuthentication(authentication);
      UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
      String accessToken = jwtTokenProvider.generateToken(userPrincipal, Boolean.FALSE);
      String refreshToken = jwtTokenProvider.generateToken(userPrincipal, Boolean.TRUE);
      return new LoginResponseDto(accessToken, refreshToken, userPrincipal.getId(), authentication.getAuthorities());
    } catch (InternalAuthenticationServiceException e) {
      throw new UnauthorizedException(ErrorMessage.Auth.ERR_INCORRECT_EMAIL);
    } catch (BadCredentialsException e) {
      throw new UnauthorizedException(ErrorMessage.Auth.ERR_INCORRECT_PASSWORD);
    }
  }

  @Override
  public RegisterResponseDto register(UserCreateDto request) {
    Optional<User> findUser = userRepository.findUserByEmail(request.getEmail());
    if(!ObjectUtils.isEmpty(findUser)) {
      throw new AlreadyExistException(ErrorMessage.User.ERR_ALREADY_EXIST_EMAIL,
              new String[]{request.getEmail()});
    }

    User user = userMapper.toUser(request);
    user.setPassword(passwordEncoder.encode(request.getPassword()));
    user.setRole(roleRepository.findByRoleName(RoleConstant.USER));
    user.setAvatar(CommonConstant.DEFAULT_AVATAR);
    userRepository.save(user);

    Wallet wallet = new Wallet();
    wallet.setName(CommonConstant.WALLET_NAME_DEFAULT);
    wallet.setTotal(CommonConstant.ZERO_INT_VALUE);
    wallet.setUser(user);
    walletRepository.save(wallet);

    DefaultCategories.CategoriesConstant.forEach(category -> {
      category.setUser(user);
      categoryRepository.save(category);
    });

    return userMapper.toRegisterResponseDto(user);
  }

  @Override
  public TokenRefreshResponseDto refresh(TokenRefreshRequestDto request) {
    return null;
  }

  @Override
  public CommonResponseDto logout(HttpServletRequest request,
                                  HttpServletResponse response, Authentication authentication) {
    new SecurityContextLogoutHandler().logout(request, response, authentication);
    return new CommonResponseDto(true, MessageConstant.LOGOUT_SUCCESSFULLY);
  }

  @Override
  public CommonResponseDto forgotPassword(ForgotPasswordRequestDto requestDto) {
    User user = userRepository.findUserByEmail(requestDto.getEmail())
            .orElseThrow(() -> {
              throw new NotFoundException(ErrorMessage.User.ERR_NOT_FOUND_USERNAME,
                      new String[]{requestDto.getEmail()});
            });

    String newPassword = RandomString.generate(CommonConstant.RANDOM_PASSWORD_LENGTH);

    Map<String, Object> props = new HashMap<>();
    props.put("fullName", user.getFullName());
    props.put("password", newPassword);
    props.put("appName", CommonConstant.APP_NAME);

    DataMailDto mail = new DataMailDto(user.getEmail(),
            MessageConstant.SUBJECT_MAIL_RESET_PASSWORD, null, props);

    try {
      sendMailUtil.sendEmailWithHTML(mail, "reset-password.html");
    } catch (Exception e) {
      log.error("Send mail failed for {}", e.getMessage());
    }

    user.setPassword(passwordEncoder.encode(newPassword));
    userRepository.save(user);
    return new CommonResponseDto(true, MessageConstant.RESET_PASSWORD);
  }

}
