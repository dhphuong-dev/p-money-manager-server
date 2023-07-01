package com.example.moneymanagerbe.domain.dto.request;

import com.example.moneymanagerbe.validator.annotation.ValidDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Min;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TransactionUpdateDto {

    private String name;

    @Min(0)
    private float total;

    @ValidDate
    private String date;

    private String location;

    private String withPerson;

    private MultipartFile image;
}
