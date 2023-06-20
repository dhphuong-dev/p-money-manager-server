package com.example.moneymanagerbe.domain.dto.request;

import com.example.moneymanagerbe.constant.ErrorMessage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CategoryRequestDto {

    @NotBlank(message = ErrorMessage.NOT_BLANK_FIELD)
    private String type;

    @NotBlank(message = ErrorMessage.NOT_BLANK_FIELD)
    private String name;

    private String imageUrl;

    @NotBlank(message = ErrorMessage.NOT_BLANK_FIELD)
    private String userId;
}
