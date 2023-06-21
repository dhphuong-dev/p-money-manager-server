package com.example.moneymanagerbe.domain.dto.request;

import com.example.moneymanagerbe.constant.ErrorMessage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TransactionUpdateDto {

    private String name;

    private float total;

    @Pattern(regexp = "^((19|20)\\d\\d)-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[01])$",
            message = ErrorMessage.INVALID_DATE)
    private String date;

    private String location;

    private String withPerson;

    private String imageUrl;
}
