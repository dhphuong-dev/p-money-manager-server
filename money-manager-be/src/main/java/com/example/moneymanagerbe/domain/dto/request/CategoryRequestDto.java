package com.example.moneymanagerbe.domain.dto.request;

import com.example.moneymanagerbe.constant.ErrorMessage;
import com.example.moneymanagerbe.validator.annotation.ValidCategoryType;
import com.example.moneymanagerbe.validator.annotation.ValidFileImage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CategoryRequestDto {

    @NotBlank(message = ErrorMessage.NOT_BLANK_FIELD)
    @ValidCategoryType
    private String type;

    @NotBlank(message = ErrorMessage.NOT_BLANK_FIELD)
    private String name;

    @ValidFileImage
    private MultipartFile image;
}
