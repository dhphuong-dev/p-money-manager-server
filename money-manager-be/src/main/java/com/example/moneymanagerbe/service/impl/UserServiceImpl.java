package com.example.moneymanagerbe.service.impl;

import com.example.moneymanagerbe.constant.CommonConstant;
import com.example.moneymanagerbe.constant.ErrorMessage;
import com.example.moneymanagerbe.constant.SortByDataConstant;
import com.example.moneymanagerbe.domain.dto.pagination.PaginationFullRequestDto;
import com.example.moneymanagerbe.domain.dto.pagination.PaginationResponseDto;
import com.example.moneymanagerbe.domain.dto.request.UserUpdateDto;
import com.example.moneymanagerbe.domain.dto.response.UserDto;
import com.example.moneymanagerbe.domain.entity.User;
import com.example.moneymanagerbe.domain.mapper.UserMapper;
import com.example.moneymanagerbe.exception.AlreadyExistException;
import com.example.moneymanagerbe.exception.NotFoundException;
import com.example.moneymanagerbe.repository.UserRepository;
import com.example.moneymanagerbe.security.UserPrincipal;
import com.example.moneymanagerbe.service.UserService;
import com.example.moneymanagerbe.util.PaginationUtil;
import com.example.moneymanagerbe.util.UploadFileUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UploadFileUtil uploadFileUtil;

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    @Override
    public UserDto getUserDtoById(String userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.User.ERR_NOT_FOUND_ID, new String[]{userId}));
        return userMapper.toUserDto(user);
    }

    @Override
    public User getUserById(String userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.User.ERR_NOT_FOUND_ID, new String[]{userId}));
    }

    @Override
    public PaginationResponseDto<UserDto> getCustomers(PaginationFullRequestDto request) {
        //Pagination
        Pageable pageable = PaginationUtil.buildPageable(request, SortByDataConstant.USER);
        //Create Output
        return new PaginationResponseDto<>(null, null);
    }

    @Override
    public UserDto getCurrentUser(UserPrincipal principal) {
        User user = userRepository.getUser(principal);
        return userMapper.toUserDto(user);
    }

    @Override
    public UserDto updateProfile(String id, UserUpdateDto userUpdateDto) {
        User user = this.getUserById(id);

        if (userUpdateDto.getFullName() != null) {
            user.setFullName(userUpdateDto.getFullName());
        }

        if (userUpdateDto.getUsername() != null) {
            if (userRepository.findByUsername(user.getUsername()).isEmpty()) {
                user.setUsername(userUpdateDto.getUsername());
            } else throw new AlreadyExistException(ErrorMessage.User.ERR_ALREADY_EXIST_EMAIL,
                    new String[]{user.getUsername()});
        }

        if (userUpdateDto.getAvatar() != null) {
            String oldAvatar = user.getAvatar();
            if (!oldAvatar.equals(CommonConstant.DEFAULT_AVATAR)) {
                uploadFileUtil.destroyFileWithUrl(oldAvatar);
            }
            String newAvatar = uploadFileUtil.uploadFile(userUpdateDto.getAvatar());
            user.setAvatar(newAvatar);
        }

        return userMapper.toUserDto(userRepository.save(user));
    }

}
