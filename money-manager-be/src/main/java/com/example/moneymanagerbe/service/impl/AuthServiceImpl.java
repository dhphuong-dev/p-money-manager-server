package com.example.moneymanagerbe.service.impl;

import com.example.moneymanagerbe.constant.CommonConstant;
import com.example.moneymanagerbe.constant.ErrorMessage;
import com.example.moneymanagerbe.constant.RoleConstant;
import com.example.moneymanagerbe.domain.dto.request.LoginRequestDto;
import com.example.moneymanagerbe.domain.dto.request.TokenRefreshRequestDto;
import com.example.moneymanagerbe.domain.dto.request.UserCreateDto;
import com.example.moneymanagerbe.domain.dto.response.CommonResponseDto;
import com.example.moneymanagerbe.domain.dto.response.LoginResponseDto;
import com.example.moneymanagerbe.domain.dto.response.RegisterResponseDto;
import com.example.moneymanagerbe.domain.dto.response.TokenRefreshResponseDto;
import com.example.moneymanagerbe.domain.entity.Budget;
import com.example.moneymanagerbe.domain.entity.User;
import com.example.moneymanagerbe.domain.mapper.UserMapper;
import com.example.moneymanagerbe.exception.AlreadyExistException;
import com.example.moneymanagerbe.exception.UnauthorizedException;
import com.example.moneymanagerbe.repository.BudgetRepository;
import com.example.moneymanagerbe.repository.RoleRepository;
import com.example.moneymanagerbe.repository.UserRepository;
import com.example.moneymanagerbe.security.UserPrincipal;
import com.example.moneymanagerbe.security.jwt.JwtTokenProvider;
import com.example.moneymanagerbe.service.AuthService;
import com.example.moneymanagerbe.service.BudgetService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

  private final AuthenticationManager authenticationManager;

  private final JwtTokenProvider jwtTokenProvider;

  private final PasswordEncoder passwordEncoder;

  private final UserRepository userRepository;

  private final RoleRepository roleRepository;

  private final BudgetRepository budgetRepository;

  private final UserMapper userMapper;

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
      throw new UnauthorizedException(ErrorMessage.Auth.ERR_INCORRECT_USERNAME);
    } catch (BadCredentialsException e) {
      throw new UnauthorizedException(ErrorMessage.Auth.ERR_INCORRECT_PASSWORD);
    }
  }

  @Override
  public RegisterResponseDto register(UserCreateDto request) {
    Optional<User> findUser = userRepository.findByUsername(request.getUsername());
    if(!ObjectUtils.isEmpty(findUser)) {
      throw new AlreadyExistException(ErrorMessage.User.ERR_ALREADY_EXIST_EMAIL,
              new String[]{request.getUsername()});
    }

    User user = userMapper.toUser(request);
    user.setPassword(passwordEncoder.encode(request.getPassword()));
    user.setRole(roleRepository.findByRoleName(RoleConstant.USER));
    user.setAvatar(CommonConstant.DEFAULT_AVATAR);
    userRepository.save(user);

    Budget budget = new Budget();
    budget.setName(CommonConstant.BUDGET_NAME_DEFAULT);
    budget.setTotal(CommonConstant.ZERO_INT_VALUE);
    budget.setUser(user);
    budgetRepository.save(budget);

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
    return new CommonResponseDto(true, "Successfully Logout");
  }

}
