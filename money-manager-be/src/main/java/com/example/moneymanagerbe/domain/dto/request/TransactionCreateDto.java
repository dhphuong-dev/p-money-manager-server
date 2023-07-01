package com.example.moneymanagerbe.domain.dto.request;

import com.example.moneymanagerbe.constant.ErrorMessage;
import com.example.moneymanagerbe.validator.annotation.ValidDate;
import com.example.moneymanagerbe.validator.annotation.ValidFileImage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TransactionCreateDto {

    @NotBlank(message = ErrorMessage.NOT_BLANK_FIELD)
    private String name;

    @NotNull(message = ErrorMessage.NOT_BLANK_FIELD)
    @Min(0)
    private float total;

    @NotBlank(message = ErrorMessage.NOT_BLANK_FIELD)
    @ValidDate
    private String date;

    private String location;

    private String withPerson;

    @ValidFileImage
    private MultipartFile image;

    @NotBlank(message = ErrorMessage.NOT_BLANK_FIELD)
    private String categoryId;

    @NotBlank(message = ErrorMessage.NOT_BLANK_FIELD)
    private String budgetId;

    @NotBlank(message = ErrorMessage.NOT_BLANK_FIELD)
    private String userId;
}
