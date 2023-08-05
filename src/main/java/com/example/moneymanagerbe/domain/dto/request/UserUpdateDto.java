package com.example.moneymanagerbe.domain.dto.request;

import com.example.moneymanagerbe.constant.ErrorMessage;
import com.example.moneymanagerbe.validator.annotation.ValidFileImage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Pattern;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserUpdateDto {

  @Pattern(regexp = "^[a-z0-9](\\.?[a-z0-9]){5,}@g(oogle)?mail\\.com$",
          message = ErrorMessage.INVALID_FORMAT_EMAIL)
  private String email;

  private String fullName;

  @ValidFileImage
  private MultipartFile avatar;

}
