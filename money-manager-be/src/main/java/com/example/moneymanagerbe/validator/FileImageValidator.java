package com.example.moneymanagerbe.validator;

import com.example.moneymanagerbe.constant.CommonConstant;
import com.example.moneymanagerbe.validator.annotation.ValidFileImage;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Objects;

public class FileImageValidator implements ConstraintValidator<ValidFileImage, MultipartFile> {

  @Override
  public boolean isValid(MultipartFile file, ConstraintValidatorContext constraintValidatorContext) {
    if (file != null) {
      String contentType = file.getContentType();
      return isSupportedContentType(Objects.requireNonNull(contentType));
    }
    return true;
  }

  private boolean isSupportedContentType(String contentType) {
    return CommonConstant.CONTENT_TYPE_IMAGE.contains(contentType.substring("image/".length()));
  }

}
