package com.example.moneymanagerbe.service.impl;

import com.example.moneymanagerbe.constant.*;
import com.example.moneymanagerbe.domain.dto.request.ChangePasswordRequestDto;
import com.example.moneymanagerbe.domain.dto.request.UserUpdateDto;
import com.example.moneymanagerbe.domain.dto.response.CommonResponseDto;
import com.example.moneymanagerbe.domain.dto.response.UserDto;
import com.example.moneymanagerbe.domain.entity.User;
import com.example.moneymanagerbe.domain.mapper.UserMapper;
import com.example.moneymanagerbe.exception.AlreadyExistException;
import com.example.moneymanagerbe.exception.NotFoundException;
import com.example.moneymanagerbe.repository.UserRepository;
import com.example.moneymanagerbe.security.UserPrincipal;
import com.example.moneymanagerbe.service.UserService;
import com.example.moneymanagerbe.util.UploadFileUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UploadFileUtil uploadFileUtil;

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    private final PasswordEncoder passwordEncoder;

    @Override
    public User getUserById(String userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.User.ERR_NOT_FOUND_ID, new String[]{userId}));
    }

    @Override
    public UserDto getCurrentUser(UserPrincipal principal) {
        User user = userRepository.getUser(principal);
        return userMapper.toUserDto(user);
    }

    @Override
    public UserDto updateProfile(String id, UserUpdateDto userUpdateDto) {
        User user = this.getUserById(id);

        if (userUpdateDto.getFullName() != null &&
                !user.getFullName().equals(userUpdateDto.getFullName())) {
            user.setFullName(userUpdateDto.getFullName());
        }

        if (userUpdateDto.getEmail() != null &&
                !user.getEmail().equals(userUpdateDto.getEmail())) {
            if (userRepository.findUserByEmail(userUpdateDto.getEmail()).isEmpty()) {
                user.setEmail(userUpdateDto.getEmail());
            } else throw new AlreadyExistException(ErrorMessage.User.ERR_ALREADY_EXIST_EMAIL,
                    new String[]{user.getEmail()});
        }

        if (userUpdateDto.getAvatar() != null) {
            String oldAvatar = user.getAvatar();
            if (!oldAvatar.equals(CommonConstant.DEFAULT_AVATAR)) {
                uploadFileUtil.destroyFileWithUrl(oldAvatar,
                        CloudinaryFolder.userAvatarFolder(user.getEmail()));
            }
            String newAvatar = uploadFileUtil.uploadFile(userUpdateDto.getAvatar(),
                    CloudinaryFolder.userAvatarFolder(user.getEmail()));
            user.setAvatar(newAvatar);
        }

        return userMapper.toUserDto(userRepository.save(user));
    }

    @Override
    public CommonResponseDto changePassword(String userId, ChangePasswordRequestDto request) {
        User user = this.getUserById(userId);

        if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
            return new CommonResponseDto(false, MessageConstant.INCORRECT_PASSWORD);
        }

        if (request.getCurrentPassword().equals(request.getNewPassword())) {
            return new CommonResponseDto(false, MessageConstant.SAME_PASSWORD);
        }

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);

        return new CommonResponseDto(true, MessageConstant.CHANGE_PASSWORD_SUCCESSFULLY);
    }

}
