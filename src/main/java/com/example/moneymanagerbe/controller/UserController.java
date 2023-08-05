package com.example.moneymanagerbe.controller;

import com.example.moneymanagerbe.base.RestApiV1;
import com.example.moneymanagerbe.base.VsResponseUtil;
import com.example.moneymanagerbe.constant.UrlConstant;
import com.example.moneymanagerbe.domain.dto.request.ChangePasswordRequestDto;
import com.example.moneymanagerbe.domain.dto.request.UserUpdateDto;
import com.example.moneymanagerbe.security.CurrentUser;
import com.example.moneymanagerbe.security.UserPrincipal;
import com.example.moneymanagerbe.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestApiV1
public class UserController {

  private final UserService userService;

  @Tag(name = "user-controller-admin")
  @Operation(summary = "API get user")
  @GetMapping(UrlConstant.User.GET_USER)
  @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
  public ResponseEntity<?> getUserById(@PathVariable String userId) {
    return VsResponseUtil.success(userService.getUserById(userId));
  }

  @Tags({@Tag(name = "user-controller-admin"), @Tag(name = "user-controller")})
  @Operation(summary = "API get current user login")
  @GetMapping(UrlConstant.User.GET_CURRENT_USER)
  public ResponseEntity<?> getCurrentUser(@Parameter(name = "principal", hidden = true)
                                          @CurrentUser UserPrincipal principal) {
    return VsResponseUtil.success(userService.getCurrentUser(principal));
  }

  @Tag(name = "user-controller")
  @Operation(summary = "API update profile by current user")
  @PatchMapping(value = UrlConstant.User.UPDATE_USER, consumes = "multipart/form-data")
  public ResponseEntity<?> updateProfile(@Valid @ModelAttribute UserUpdateDto userUpdateDto,
                                         @Parameter(name = "user", hidden = true)
                                           @CurrentUser UserPrincipal user) {
    return VsResponseUtil.success(userService.updateProfile(user.getId(), userUpdateDto));
  }

  @Tag(name = "user-controller")
  @Operation(summary = "API change user's password")
  @PostMapping(value = UrlConstant.User.CHANGE_PASSWORD)
  public ResponseEntity<?> changePassword(@Valid @RequestBody ChangePasswordRequestDto passwordRequestDto,
                                          @Parameter(name = "user", hidden = true)
                                          @CurrentUser UserPrincipal user) {
    return VsResponseUtil.success(userService.changePassword(user.getId(), passwordRequestDto));
  }

}
