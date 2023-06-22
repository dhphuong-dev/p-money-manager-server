package com.example.moneymanagerbe.domain.dto.response;

import com.example.moneymanagerbe.constant.CommonConstant;
import com.example.moneymanagerbe.domain.dto.common.DateAuditingDto;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TransactionResponseDto extends DateAuditingDto {

    private String id;

    private String name;

    private float total;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = CommonConstant.PATTERN_DATE)
    private String date;

    private String location;

    private String withPerson;

    private String imageUrl;

    private String categoryId;

    private String budgetId;
}
