package com.example.moneymanagerbe.domain.dto.request;

import com.example.moneymanagerbe.constant.ErrorMessage;
import com.example.moneymanagerbe.validator.annotation.ValidFileImage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

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
    @Pattern(regexp = "^((19|20)\\d\\d)-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[01])$",
            message = ErrorMessage.INVALID_DATE)
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
